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
import model.RoonerModel
import model.RoonerModel.UiEvent.*

@Composable
fun ToolBar(model: RoonerModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = { model.action(RunCode) }
        ) {
            Text(
                text = "\uf04b",
                color = Color.Green,
                fontSize = 24.sp
            )
        }
    }
}