package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditorPane() {
    Box(modifier = Modifier.fillMaxWidth(.5f).fillMaxHeight()) {
        Text("Editor pane here")
    }
}