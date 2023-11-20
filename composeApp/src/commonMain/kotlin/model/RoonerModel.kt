package model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import controller.models.ExitStatus
import model.RoonerModel.UiEvent.*

class RoonerModel {
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState>
        get() = _uiState

    sealed class UiEvent {
        data class EditorTextChange(val newText: TextFieldValue) : UiEvent()
    }

    fun action(event: UiEvent) {
        when (event) {
            is EditorTextChange -> {
                _uiState.value = uiState.value.copy(text = event.newText)
            }
        }
    }
}

data class UiState(
    var text: TextFieldValue = TextFieldValue(""),
    var code: ExitStatus = ExitStatus.None,
    var isRunning: Boolean = false
)