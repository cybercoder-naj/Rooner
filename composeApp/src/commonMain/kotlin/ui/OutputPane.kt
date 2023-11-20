package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import controller.models.OutputString
import model.RoonerModel
import ui.components.Pane

@Composable
fun OutputPane(model: RoonerModel) {
    val output = model.output.collectAsState()
    Pane(
        title = "Output",
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 12.dp, start = 8.dp)
        ) {
            items(output.value) {
                Text(
                    text = it.message,
                    color = when(it) {
                        is OutputString.StdOut -> MaterialTheme.colors.onBackground
                        is OutputString.StdErr -> MaterialTheme.colors.error
                    }
                )
            }
        }
    }
}