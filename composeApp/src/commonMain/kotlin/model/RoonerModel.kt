package model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import controller.Controller
import controller.models.ExitStatus
import model.RoonerModel.UiEvent.EditCode
import model.RoonerModel.UiEvent.RunCode

class RoonerModel(
    val controller: Controller
) {
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState>
        get() = _uiState

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
                controller.runCode(uiState.value.text.text)
            }
        }
    }
}

data class UiState(
    var text: TextFieldValue = TextFieldValue(""),
    var exitStatus: ExitStatus = ExitStatus.None,
    var isRunning: Boolean = false
)