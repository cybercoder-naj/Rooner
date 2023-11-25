package di

import data.KotlinLanguageSetting
import data.StdoutLogger
import data.repositories.TimeAnalyticsRepositoryImpl
import domain.LanguageSetting
import domain.Logger
import domain.repositories.TimeAnalyticsRepository
import ui.RoonerViewModel

@Suppress("MemberVisibilityCanBePrivate")
object AppContainer {
    val logger: Logger = StdoutLogger()

    val timeAnalytics: TimeAnalyticsRepository = TimeAnalyticsRepositoryImpl(logger)

    val languageSetting: LanguageSetting = KotlinLanguageSetting()

    val repository: domain.repositories.CodeRunnerRepository = data.repositories.CodeRunnerRepositoryImpl(languageSetting)

    val viewModel: RoonerViewModel = RoonerViewModel(repository, languageSetting, timeAnalytics)
}