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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.ProcessStatus
import kotlinx.coroutines.flow.StateFlow
import ui.RoonerViewModel
import ui.RoonerViewModel.UiEvent.SetCursor
import ui.components.Pane
import utils.Constants
import utils.Icons

@Composable
fun OutputPane(
    runningStatus: ProcessStatus,
    output: StateFlow<AnnotatedString>,
    eta: Pair<Long, Long>,
    onAction: (RoonerViewModel.UiEvent) -> Unit
) {
    val outputState = output.collectAsState()

    Pane(
        title = "Output",
        modifier = Modifier.fillMaxSize(),
        auxiliaryInfo = { Indicator(runningStatus, eta.first / 1000L + 1) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    if (runningStatus !is ProcessStatus.Active)
                        return@drawBehind

                    val dx =
                        (eta.first.toFloat() / eta.second.toFloat()) * size.width
                    drawLine(
                        color = Color.Green,
                        start = Offset(0f, 0f),
                        end = Offset(dx, 0f)
                    )
                }
                .padding(start = 12.dp, top = 12.dp)
        ) {
            ClickableText(
                text = outputState.value,
                onClick = {
                    outputState.value
                        .getStringAnnotations(
                            Constants.SCRIPT_ERROR_LOCATION,
                            it,
                            it
                        )
                        .firstOrNull()?.let { cursorPosition ->
                            val params = cursorPosition.item.split(":").map(String::toInt)
                            if (params.size == 1)
                                onAction(SetCursor(params[0], 1))
                            else
                                onAction(SetCursor(params[0], params[1]))
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