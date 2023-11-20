package controller.models

sealed class OutputString(val message: String){
    class StdOut(message: String): OutputString(message)
    class StdErr(message: String): OutputString(message)
}
