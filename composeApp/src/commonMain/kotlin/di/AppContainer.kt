package di

import data.executables.KotlinExecutable
import data.repositories.CodeRunnerRepositoryImpl
import domain.executables.Executable
import domain.repositories.CodeRunnerRepository
import ui.RoonerViewModel

object AppContainer {
    val executable: Executable = KotlinExecutable()

    val repository: CodeRunnerRepository = CodeRunnerRepositoryImpl(executable)

    val viewModel: RoonerViewModel = RoonerViewModel(repository)
}