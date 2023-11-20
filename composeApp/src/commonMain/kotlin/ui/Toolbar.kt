package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToolBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text("Here goes some indicator icons")
    }
}