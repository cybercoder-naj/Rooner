package ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.ProcessStatus
import di.AppContainer
import ui.RoonerViewModel
import ui.RoonerViewModel.UiEvent.SetCursor
import ui.components.Pane
import utils.Constants
import utils.Icons

@Composable
fun OutputPane(viewModel: RoonerViewModel = AppContainer.viewModel) {
    val output = viewModel.output.collectAsState()
    val runningStatus = viewModel.runningStatus

    Pane(
        title = "Output",
        modifier = Modifier.fillMaxSize(),
        auxiliaryInfo = { Indicator(runningStatus, viewModel.eta.first / 1000L + 1) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    if (runningStatus !is ProcessStatus.Active)
                        return@drawBehind

                    val dx =
                        (viewModel.eta.first.toFloat() / viewModel.eta.second.toFloat()) * size.width
                    drawLine(
                        color = Color.Green,
                        start = Offset(0f, 0f),
                        end = Offset(dx, 0f)
                    )
                }
                .padding(start = 12.dp, top = 12.dp)
        ) {
            ClickableText(
                text = output.value,
                onClick = {
                    output.value
                        .getStringAnnotations(
                            Constants.SCRIPT_ERROR_LOCATION,
                            it,
                            it
                        )
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
                ),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}

@Composable
fun Indicator(runningStatus: ProcessStatus, eta: Long) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (runningStatus) {
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
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "ETA: $eta sec(s)"
                )
            }

            is ProcessStatus.Done -> {
                if (runningStatus.isSuccessful) {
                    Text(
                        text = Icons.CHECKMARK,
                        color = Color.Green
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Success",
                        color = Color.Green
                    )
                } else {
                    Text(
                        text = Icons.XCROSS,
                        color = Color.Red
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Failed (exit status ${runningStatus.status})",
                        color = Color.Red
                    )
                }
            }

            ProcessStatus.Inactive -> Unit
        }
    }
}