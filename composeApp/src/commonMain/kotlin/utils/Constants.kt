package utils

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object Constants {
    const val FILENAME = "script"

    const val SCRIPT_ERROR_LOCATION = "cursorSet"

    val FONT_SIZE get() = 16.sp
    val LINE_HEIGHT get() = 1.em
    val PADDING_TOP get() = 12.dp

    const val ETA_DELAY_TIME = 1000L
}

object ExitValue {
    const val SUCCESS = 0
    const val FAILURE = 1
    const val SIGINT = 130
}

object Icons {
    const val PLAY_BUTTON = "\uf04b"
    const val STOP_BUTTON = "\uf04d"
    const val CHECKMARK = "\uf00c"
    const val XCROSS = "\uf00d"
    const val CDOT = "\uf111"
}