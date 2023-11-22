package domain

import data.models.ProcessOutput
import kotlinx.coroutines.flow.Flow

interface RoonerRepository {
    fun runCode(code: String): Flow<ProcessOutput>
}