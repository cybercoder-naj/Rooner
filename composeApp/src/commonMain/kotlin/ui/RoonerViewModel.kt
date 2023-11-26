package ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import domain.LanguageSetting
import domain.models.ProcessOutput
import domain.models.ProcessStatus
import domain.repositories.CodeRunnerRepository
import domain.repositories.TimeAnalyticsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.RoonerViewModel.UiEvent.EditCode
import ui.RoonerViewModel.UiEvent.RunCode
import utils.highlight

class RoonerViewModel(
    private val repository: CodeRunnerRepository,
    private val languageSetting: LanguageSetting,
    private val timeAnalyticsRepository: TimeAnalyticsRepository
) {
    var uiState by mutableStateOf(UiState())
        private set

    var text by mutableStateOf(TextFieldValue())
        private set

    private val _output = MutableStateFlow(buildAnnotatedString { })
    val output: StateFlow<AnnotatedString>
        get() = _output

    private var runJob: Job? = null
    private var timeJob: Job? = null
    private var startTime = 0L

    sealed class UiEvent {
        data class EditCode(val newText: TextFieldValue) : UiEvent()
        data object RunCode : UiEvent()
        data object StopCode : UiEvent()
        data object ToggleAutoClear : UiEvent()
        data class SetCursor(val row: Int, val col: Int) : UiEvent()
    }

    fun action(event: UiEvent) {
        when (event) {
            is EditCode -> {
                text = event.newText.copy(
                    annotatedString = highlight(event.newText.text, languageSetting)
                )
            }

            RunCode -> {
                if (uiState.autoClear)
                    _output.value = buildAnnotatedString { }

                if (text.text.isBlank()) {
                    val errStr = ProcessOutput.ErrorString("There is nothing to run!")
                    addToOutput(errStr)
                }

                startCode()
            }

            UiEvent.StopCode -> {
                runJob?.cancel()

                // TODO use constant instead of magic number 130
                endCode(130)
            }

            UiEvent.ToggleAutoClear -> {
                uiState = uiState.copy(autoClear = !uiState.autoClear)
            }

            is UiEvent.SetCursor -> {
                text = text.copy(
                    selection = getSelection(event)
                )
            }
        }
    }

    private fun startCode() {
        val eta = timeAnalyticsRepository.getETA()
        uiState = uiState.copy(
            runningStatus = ProcessStatus.Active,
            eta = eta,
            initialEta = eta
        )

        timeJob = CoroutineScope(Dispatchers.Default).launch {
            while (uiState.eta > 0) {
                delay(1000L)
                uiState = uiState.copy(
                    eta = maxOf(uiState.eta - 1000L, 0L)
                )
            }
        }

        startTime = System.currentTimeMillis()
        runJob = CoroutineScope(Dispatchers.Default).launch {
            repository.runCode(text.text).collect {
                when (it) {
                    is ProcessOutput.Complete -> endCode(it.status)
                    else -> addToOutput(it)
                }
            }
        }
    }

    private fun endCode(status: Int) {
        uiState =
            uiState.copy(runningStatus = ProcessStatus.Done(status))
        val time = System.currentTimeMillis() - startTime

        timeJob?.cancel()
        timeAnalyticsRepository.recordTime(time)
    }

    private fun getSelection(event: UiEvent.SetCursor): TextRange {
        val lines = text.text.lines()
        var index = 0
        for (i in 0..<(event.row - 1))
            index += lines[i].length + 1
        return TextRange(index + event.col - 1, index + event.col)
    }

    private fun addToOutput(output: ProcessOutput) {
        assert(output !is ProcessOutput.Complete)

        val transformed = buildAnnotatedString {
            when (output) {
                is ProcessOutput.OutputString -> appendLine(output.message)
                is ProcessOutput.ErrorString ->
                    withStyle(style = SpanStyle(color = Color.Red)) { // TODO change colors of output pane
                        appendLine(makeClickable(output.message))
                    }

                else -> throw IllegalStateException("Assertion failed")
            }
        }

        _output.value = this.output.value + transformed
    }

    private fun makeClickable(string: String): AnnotatedString {
        val regexFilename = languageSetting.filename.replace(".", "\\.")
        val match = Regex("$regexFilename:(\\d+(?::\\d+)?)").find(string)
            ?: return buildAnnotatedString { append(string) }

        return buildAnnotatedString {
            append(string.substringBefore(match.groupValues[0]))
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(match.groupValues[0])
            }
            append(string.substringAfterLast(match.groupValues[0]))

            val index = string.indexOf(match.groupValues[0])
            addStringAnnotation(
                tag = "cursorSet",
                annotation = match.groupValues[1],
                start = index,
                end = index + match.groupValues[0].length
            )
        }
    }

    data class UiState(
        var runningStatus: ProcessStatus = ProcessStatus.Inactive,
        var autoClear: Boolean = false,
        var eta: Long = 0L,
        var initialEta: Long = 0L // TODO need to change all of this
    )
}
