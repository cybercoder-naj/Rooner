package ui

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import di.AppContainer
import model.RoonerModel
import model.RoonerModel.UiEvent.EditCode
import ui.components.Pane

@Composable
fun EditorPane(model: RoonerModel = AppContainer.model) {
    val state = model.uiState.value
    val editorFontSize = 16.sp
    val editorLineHeight = 1.em
    val editorPaddingTop = 12.dp
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
                .padding(top = editorPaddingTop)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 8.dp, end = 12.dp)
            ) {
                for (i in state.text.text.lines().indices) {
                    Text(
                        text = "${i + 1}",
                        fontFamily = FontFamily.Monospace,
                        fontSize = editorFontSize,
                        lineHeight = editorLineHeight,
                        color = Color.LightGray
                    )
                }
            }

            CompositionLocalProvider(LocalTextSelectionColors provides selectionColours) {
                BasicTextField(
                    value = state.text,
                    onValueChange = { model.action(EditCode(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                        .padding(start = 12.dp),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = editorFontSize,
                        lineHeight = editorLineHeight
                    ),
                    cursorBrush = Brush.linearGradient(listOf(Color.White, Color.White))
                )
            }
        }
    }
}