package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.components.Pane

@Composable
fun OutputPane() {
    Pane(
        title = "Output",
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Output Pane Hahh")
    }
}