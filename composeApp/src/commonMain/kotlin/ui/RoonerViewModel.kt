package ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import domain.RoonerRepository
import data.models.ProcessStatus
import data.models.ProcessOutput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.RoonerViewModel.UiEvent.EditCode
import ui.RoonerViewModel.UiEvent.RunCode

class RoonerViewModel(
    private val repository: RoonerRepository
) {
    // TODO merge uiState into stateflow
    private val _uiState = mutableStateOf(UiState())
    val uiState: State<UiState>
        get() = _uiState

    private val _output = MutableStateFlow(emptyList<ProcessOutput>())
    val output: StateFlow<List<ProcessOutput>>
        get() = _output

    private var runJob: Job? = null

    sealed class UiEvent {
        data class EditCode(val newText: TextFieldValue) : UiEvent()
        data object RunCode : UiEvent()
        data object StopCode : UiEvent()
    }

    fun action(event: UiEvent) {
        when (event) {
            is EditCode -> {
                _uiState.value = uiState.value.copy(text = event.newText)
            }

            RunCode -> {
                _uiState.value = uiState.value.copy(runningStatus = ProcessStatus.Active)
                runJob = CoroutineScope(Dispatchers.Default).launch {
                    repository.runCode(uiState.value.text.text).collect {
                        when (it) {
                            is ProcessOutput.Complete ->
                                _uiState.value = uiState.value.copy(runningStatus = ProcessStatus.Done(it.status))
                            else ->
                                _output.value = output.value + it
                        }
                    }
                }
            }

            UiEvent.StopCode -> {
                runJob?.cancel()
                // TODO use constant instead of magic number 130
                _uiState.value = uiState.value.copy(runningStatus = ProcessStatus.Done(130))
            }
        }
    }
}

data class UiState(
    var text: TextFieldValue = TextFieldValue(""),
    var runningStatus: ProcessStatus = ProcessStatus.Inactive,
)