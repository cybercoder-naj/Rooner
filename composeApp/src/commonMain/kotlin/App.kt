import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.EditorPane
import ui.OutputPane
import ui.ToolBar

@Composable
fun App() {
    RoonerTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                ToolBar()
                Row {
                    EditorPane()
                    OutputPane()
                }
            }
        }
    }
}

@Composable
fun RoonerTheme(content: @Composable () -> Unit) {
    val darcula = Color(0xFF1E1F22)
    val colors = darkColors(
        primary = darcula,
        background = Color.DarkGray,
        surface = darcula,
        error = Color(0xFFB00020), // Error Red
        secondary = Color(0xFF0059FF), // Blue800,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
        onError = Color.White
    )

    MaterialTheme(
        colors = colors,
        content = content,
    )
}
