package domain.repositories

import domain.models.ProcessOutput
import kotlinx.coroutines.flow.Flow

interface RoonerRepository {
    fun runCode(code: String): Flow<ProcessOutput>
}