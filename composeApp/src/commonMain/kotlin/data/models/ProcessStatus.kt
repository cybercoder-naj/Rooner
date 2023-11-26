package data.models

sealed class ProcessStatus {
    data object Inactive : ProcessStatus()
    data object Active : ProcessStatus()
    data class Done(val status: Int) : ProcessStatus()

}
