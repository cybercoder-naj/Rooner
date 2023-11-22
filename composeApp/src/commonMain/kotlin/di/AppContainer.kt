package di

import data.repositories.RoonerRepositoryImpl
import domain.RoonerRepository
import data.repositories.TestRepository
import ui.RoonerViewModel

object AppContainer {
    val repository: RoonerRepository = RoonerRepositoryImpl()

    val viewModel: RoonerViewModel = RoonerViewModel(repository)
}