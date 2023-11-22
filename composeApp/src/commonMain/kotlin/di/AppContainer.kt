package di

import data.repositories.RoonerRepositoryImpl
import domain.repositories.RoonerRepository
import ui.RoonerViewModel

object AppContainer {
    val repository: RoonerRepository = RoonerRepositoryImpl()

    val viewModel: RoonerViewModel = RoonerViewModel(repository)
}