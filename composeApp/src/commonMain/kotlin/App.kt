import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import di.AppContainer
import ui.screen.EditorPane
import ui.screen.OutputPane
import ui.screen.ToolBar

@Composable
fun App() {
    RoonerTheme {
        Surface(color = MaterialTheme.colors.background) {
            val viewModel = AppContainer.viewModel
            Column {
                ToolBar(
                    runningStatus = viewModel.runningStatus,
                    autoClear = viewModel.autoClear,
                    onAction = viewModel::action
                )
                Row {
                    EditorPane(
                        codeText = viewModel.text,
                        onAction = viewModel::action
                    )
                    OutputPane(
                        runningStatus = viewModel.runningStatus,
                        output = viewModel.output,
                        eta = viewModel.eta,
                        onAction = viewModel::action
                    )
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
