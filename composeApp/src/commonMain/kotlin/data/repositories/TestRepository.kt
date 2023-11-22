package data.repositories

import domain.models.ProcessOutput
import domain.repositories.RoonerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TestRepository : RoonerRepository {
    override fun runCode(code: String) = flow {
        for (i in 1..3) {
            emit(ProcessOutput.OutputString("Running process, stdout output!"))
            delay(500L)
            emit(ProcessOutput.ErrorString("Running process, stderr output!"))
            delay(500L)
        }

        emit(ProcessOutput.Complete(0))
    }
}
