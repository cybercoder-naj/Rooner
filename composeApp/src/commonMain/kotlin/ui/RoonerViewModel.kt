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
import data.models.CodeRunnerOutput
import data.models.ProcessStatus
import domain.LanguageSetting
import domain.repositories.CodeRunnerRepository
import domain.repositories.TimeAnalyticsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.RoonerViewModel.UiEvent.EditCode
import ui.RoonerViewModel.UiEvent.RunCode
import utils.Constants
import utils.ExitValue
import utils.combine
import utils.splitBy

class RoonerViewModel(
    private val codeRunnerRepository: CodeRunnerRepository,
    private val languageSetting: LanguageSetting,
    private val timeAnalyticsRepository: TimeAnalyticsRepository
) {
    var text by mutableStateOf(TextFieldValue())
        private set

    var runningStatus by mutableStateOf<ProcessStatus>(ProcessStatus.Inactive)
        private set

    var autoClear by mutableStateOf(false)
        private set

    var eta by mutableStateOf(0L to 0L)
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
                if (autoClear)
                    _output.value = buildAnnotatedString { }

                if (text.text.isBlank()) {
                    val errStr = CodeRunnerOutput.ErrorString("There is nothing to run!")
                    addToOutput(errStr)
                }

                startCode()
            }

            UiEvent.StopCode -> {
                runJob?.cancel()

                endCode(ExitValue.SIGINT)
            }

            UiEvent.ToggleAutoClear -> {
                autoClear = !autoClear
            }

            is UiEvent.SetCursor -> {
                text = text.copy(
                    selection = getSelection(event)
                )
            }
        }
    }

    private fun startCode() {
        val newEta = timeAnalyticsRepository.getETA()
        runningStatus = ProcessStatus.Active
        eta = newEta to newEta

        timeJob = CoroutineScope(Dispatchers.Default).launch {
            while (eta.first > 0) {
                delay(1000L)
                eta = eta.copy(first = eta.first - 1000L)
            }
        }

        startTime = System.currentTimeMillis()
        runJob = CoroutineScope(Dispatchers.Default).launch {
            codeRunnerRepository.runCode(text.text)
                .map(CodeRunnerOutput::fromProcessOutput)
                .collect {
                    when (it) {
                        is CodeRunnerOutput.Complete -> endCode(it.status)
                        else -> addToOutput(it)
                    }
                }
        }
    }

    private fun endCode(status: Int) {
        runningStatus = ProcessStatus.Done(status)
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

    private fun addToOutput(output: CodeRunnerOutput) {
        assert(output !is CodeRunnerOutput.Complete)

        val transformed = buildAnnotatedString {
            when (output) {
                is CodeRunnerOutput.OutputString -> appendLine(output.message)
                is CodeRunnerOutput.ErrorString ->
                    withStyle(style = SpanStyle(color = Color.Red)) {
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
                    color = Color(0xFF85a3e0),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(match.groupValues[0])
            }
            append(string.substringAfterLast(match.groupValues[0]))

            val index = string.indexOf(match.groupValues[0])
            addStringAnnotation(
                tag = Constants.SCRIPT_ERROR_LOCATION,
                annotation = match.groupValues[1],
                start = index,
                end = index + match.groupValues[0].length
            )
        }
    }

    private fun highlight(
        code: String,
        languageSetting: LanguageSetting
    ): AnnotatedString {
        val (words, separators) = code.splitBy()

        val annotatedWords = words.map {
            buildAnnotatedString {
                if (it in languageSetting.keywords)
                    withStyle(style = SpanStyle(color = languageSetting.keywords[it]!!)) {
                        append(it)
                    }
                else append(it)
            }
        }

        return annotatedWords.combine(separators) { AnnotatedString.Builder() }.toAnnotatedString()
    }

}
