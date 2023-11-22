package di

import data.executables.KotlinExecutable
import data.repositories.RoonerRepositoryImpl
import domain.executables.Executable
import domain.repositories.RoonerRepository
import ui.RoonerViewModel

object AppContainer {
    val executable: Executable = KotlinExecutable()

    val repository: RoonerRepository = RoonerRepositoryImpl(executable)

    val viewModel: RoonerViewModel = RoonerViewModel(repository)
}