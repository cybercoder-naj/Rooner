package data.models

sealed class ProcessStatus(val status: Int? = null) {
    data object Inactive : ProcessStatus()
    data object Active : ProcessStatus()
    class Done(status: Int) : ProcessStatus(status)

}
