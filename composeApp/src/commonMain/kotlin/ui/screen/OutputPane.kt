package ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import di.AppContainer
import domain.models.ProcessOutput
import domain.models.ProcessStatus
import ui.RoonerViewModel
import ui.components.Pane

@Composable
fun OutputPane(viewModel: RoonerViewModel = AppContainer.viewModel) {
    val output = viewModel.output.collectAsState()
    val state = viewModel.uiState.value

    val indicator: @Composable () -> Unit = { // TODO make a composable function
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (state.runningStatus) {
                ProcessStatus.Active -> {
                    Text(
                        text = "\uf111",
                        color = Color.Yellow
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Running",
                        color = Color.Yellow
                    )
                }

                is ProcessStatus.Done -> {
                    val done = state.runningStatus as ProcessStatus.Done
                    if (done.status == 0) { // TODO change to function
                        Text(
                            text = "\uf00c", // TODO use constants instead
                            color = Color.Green
                        )
                        Spacer(Modifier.width(4.dp)) // TODO move to constant
                        Text(
                            text = "Success",
                            color = Color.Green
                        )
                    } else {
                        Text(
                            text = "\uf00d", // TODO use constants instead
                            color = Color.Red
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Failed (exit status ${done.status})",
                            color = Color.Red
                        )
                    }
                }
                ProcessStatus.Inactive -> Unit
            }
        }
    }

    Pane(
        title = "Output",
        modifier = Modifier.fillMaxSize(),
        auxiliaryInfo = indicator
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp, start = 8.dp)
            ) {
                items(output.value) {
                    when (it) {
                        is ProcessOutput.Complete -> ErrorText(
                            text = "IDE Error occurred: ProcessOutput.Complete found in RoonerModel.output"
                        )

                        is ProcessOutput.ErrorString -> ErrorText(
                            text = it.message
                        )

                        is ProcessOutput.OutputString -> StandardText(
                            text = it.message
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StandardText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.error
    )
}
