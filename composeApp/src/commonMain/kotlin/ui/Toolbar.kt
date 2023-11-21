package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.models.ProcessStatus
import model.RoonerModel
import model.RoonerModel.UiEvent.RunCode
import model.RoonerModel.UiEvent.StopCode

private const val PLAY_ICON = "\uf04b"
private const val STOP_ICON = "\uf04d"

@Composable
fun ToolBar(model: RoonerModel) {
    val isProcessRunning = model.uiState.value.runningStatus is ProcessStatus.Active
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        if (isProcessRunning) {
            IconButton(
                onClick = { model.action(StopCode) },
            ) {
                Text(
                    text = STOP_ICON,
                    color = Color.Red,
                    fontSize = 24.sp,
                )
            }
        } else {
            IconButton(
                onClick = { model.action(RunCode) }
            ) {
                Text(
                    text = PLAY_ICON,
                    color = Color.Green,
                    fontSize = 24.sp
                )
            }
        }
    }
}