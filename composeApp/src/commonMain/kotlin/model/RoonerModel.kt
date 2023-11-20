package model

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import controller.Controller
import controller.models.ExitStatus
import controller.models.OutputString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.RoonerModel.UiEvent.EditCode
import model.RoonerModel.UiEvent.RunCode

class RoonerModel(
    private val controller: Controller
) {
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState>
        get() = _uiState

    private val _output = MutableStateFlow(emptyList<OutputString>())
    val output: StateFlow<List<OutputString>>
        get() = _output

    sealed class UiEvent {
        data class EditCode(val newText: TextFieldValue) : UiEvent()
        data object RunCode : UiEvent()
    }

    fun action(event: UiEvent) {
        when (event) {
            is EditCode -> {
                _uiState.value = uiState.value.copy(text = event.newText)
            }

            RunCode -> {
                CoroutineScope(Dispatchers.Default).launch {
                    controller.runCode(uiState.value.text.text).collect {
                        _output.value = output.value + it
                    }
                }
            }
        }
    }
}

data class UiState(
    var text: TextFieldValue = TextFieldValue(""),
    var exitStatus: ExitStatus = ExitStatus.None,
    var isRunning: Boolean = false
)