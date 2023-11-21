package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import controller.models.ProcessOutput
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
