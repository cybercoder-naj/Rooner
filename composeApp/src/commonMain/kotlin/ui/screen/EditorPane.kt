package ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import ui.RoonerViewModel
import ui.RoonerViewModel.UiEvent.EditCode
import ui.components.Pane
import utils.Constants

@Composable
fun EditorPane(
    codeText: TextFieldValue,
    onAction: (RoonerViewModel.UiEvent) -> Unit
) {
    val selectionColours = TextSelectionColors(
        handleColor = Color.Blue,
        backgroundColor = Color.Blue
    )

    Pane(
        title = "Editor",
        modifier = Modifier
            .fillMaxWidth(.5f)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Constants.PADDING_TOP)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 8.dp, end = 12.dp)
            ) {
                for (i in codeText.text.lines().indices) {
                    Text(
                        text = "${i + 1}",
                        fontFamily = FontFamily.Monospace, // Improvement: introduce settings
                        fontSize = Constants.FONT_SIZE,
                        lineHeight = Constants.LINE_HEIGHT,
                        color = Color.LightGray
                    )
                }
            }

            CompositionLocalProvider(LocalTextSelectionColors provides selectionColours) {
                BasicTextField(
                    value = codeText,
                    onValueChange = { onAction(EditCode(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                        .padding(start = 12.dp),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = Constants.FONT_SIZE,
                        lineHeight = Constants.LINE_HEIGHT
                    ),
                    cursorBrush = Brush.linearGradient(listOf(Color.White, Color.White))
                )
            }
        }
    }
}