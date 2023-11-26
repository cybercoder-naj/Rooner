package data.models

import utils.ExitValue

sealed class ProcessStatus {
    data object Inactive : ProcessStatus()
    data object Active : ProcessStatus()
    data class Done(val status: Int) : ProcessStatus() {
        val isSuccessful: Boolean
            get() = status == ExitValue.SUCCESS
    }

}
