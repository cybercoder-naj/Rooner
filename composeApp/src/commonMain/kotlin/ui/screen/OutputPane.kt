package ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.AppContainer
import domain.models.ProcessStatus
import ui.RoonerViewModel
import ui.RoonerViewModel.UiEvent.SetCursor
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
                .padding(start = 12.dp, top = 12.dp)
        ) {
            ClickableText(
                text = output.value,
                onClick = {
                    output.value
                        .getStringAnnotations("cursorSet", it, it) // TODO cursorSet move to constant
                        .firstOrNull()?.let { cursorPosition ->
                            val params = cursorPosition.item.split(":").map(String::toInt)
                            if (params.size == 1)
                                viewModel.action(SetCursor(params[0], 1))
                            else
                                viewModel.action(SetCursor(params[0], params[1]))
                        }
                },
                style = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp
                )
            )
        }
    }
}