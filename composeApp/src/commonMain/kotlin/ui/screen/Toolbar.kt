package ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.ProcessStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.RoonerViewModel
import ui.RoonerViewModel.UiEvent.*
import utils.Icons

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToolBar(
    runningStatus: ProcessStatus,
    autoClear: Boolean,
    onAction: (RoonerViewModel.UiEvent) -> Unit
) {
    val isProcessRunning = runningStatus is ProcessStatus.Active
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Improvement: this into a separate file
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                .padding(start = 4.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = autoClear,
                onCheckedChange = { onAction(ToggleAutoClear) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White
                )
            )
            Text("Auto-Clear")
        }
        Spacer(Modifier.width(12.dp))
        if (isProcessRunning) {
            val anim = remember { Animatable(0f) }
            LaunchedEffect(true) {
                anim.animateTo(
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 1000, // Improvement: use a Constant instead
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

            IconButton(
                onClick = { onAction(StopCode) },
            ) {
                Text(
                    text = Icons.STOP_BUTTON,
                    color = Color.Red,
                    fontSize = 24.sp,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painterResource("running.png"),
                null,
                modifier = Modifier.size(32.dp).rotate(anim.value)
            )
        } else {
            IconButton(
                onClick = { onAction(RunCode) }
            ) {
                Text(
                    text = Icons.PLAY_BUTTON,
                    color = Color.Green,
                    fontSize = 24.sp
                )
            }
        }
    }
}

