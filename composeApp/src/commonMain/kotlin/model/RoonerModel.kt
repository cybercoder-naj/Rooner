package model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import controller.models.ExitStatus

class RoonerModel {
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState>
        get() = _uiState
}

data class UiState(
    var text: List<String> = emptyList(),
    var code: ExitStatus = ExitStatus.None,
    var isRunning: Boolean = false
)