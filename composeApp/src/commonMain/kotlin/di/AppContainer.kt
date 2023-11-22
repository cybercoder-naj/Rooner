package di

import data.KotlinLanguageSetting
import data.repositories.CodeRunnerRepositoryImpl
import domain.LanguageSetting
import domain.repositories.CodeRunnerRepository
import ui.RoonerViewModel

object AppContainer {
    val languageSetting: LanguageSetting = KotlinLanguageSetting()

    val repository: CodeRunnerRepository = CodeRunnerRepositoryImpl(languageSetting)

    val viewModel: RoonerViewModel = RoonerViewModel(repository)
}