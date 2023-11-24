package ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.RoonerViewModel.UiEvent.EditCode
import ui.RoonerViewModel.UiEvent.RunCode
import utils.highlight

class RoonerViewModel(
    private val repository: CodeRunnerRepository,
    private val languageSetting: LanguageSetting
) {
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState>
        get() = _uiState

    private val _output = MutableStateFlow(buildAnnotatedString { })
    val output: StateFlow<AnnotatedString>
        get() = _output

    private var runJob: Job? = null

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
                _uiState.value = uiState.value.copy(
                    text = event.newText.copy(
                        annotatedString = highlight(event.newText.text, languageSetting)
                    )
                )
            }

            RunCode -> {
                if (uiState.value.autoClear)
                    _output.value = buildAnnotatedString { }

                if (uiState.value.text.text.isBlank()) {
                    val errStr = ProcessOutput.ErrorString("There is nothing to run!")
                    addToOutput(errStr)
                }

                _uiState.value = uiState.value.copy(runningStatus = ProcessStatus.Active)

                runJob = CoroutineScope(Dispatchers.Default).launch {
                    repository.runCode(uiState.value.text.text).collect {
                        when (it) {
                            is ProcessOutput.Complete ->
                                _uiState.value =
                                    uiState.value.copy(runningStatus = ProcessStatus.Done(it.status))

                            else -> addToOutput(it)
                        }
                    }
                }
            }

            UiEvent.StopCode -> {
                runJob?.cancel()
                // TODO use constant instead of magic number 130
                _uiState.value = uiState.value.copy(runningStatus = ProcessStatus.Done(130))
            }

            UiEvent.ToggleAutoClear -> {
                _uiState.value = uiState.value.copy(autoClear = !uiState.value.autoClear)
            }

            is UiEvent.SetCursor -> {
                _uiState.value = uiState.value.copy(
                    text = uiState.value.text.copy(
                        selection = getSelection(event)
                    )
                )
            }
        }
    }

    private fun getSelection(event: UiEvent.SetCursor): TextRange {
        val lines = uiState.value.text.text.lines()
        var index = 0
        for (i in 0..<(event.row - 1))
            index += lines[i].length
        return TextRange(index + event.col - 1, index + event.col)
    }

    private fun addToOutput(output: ProcessOutput) {
        assert(output !is ProcessOutput.Complete)

        val transformed = buildAnnotatedString {
            when (output) {
                is ProcessOutput.OutputString -> appendLine(output.message)
                is ProcessOutput.ErrorString ->
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
        var text: TextFieldValue = TextFieldValue(buildAnnotatedString {}),
        var runningStatus: ProcessStatus = ProcessStatus.Inactive,
        var autoClear: Boolean = false
    )
}
