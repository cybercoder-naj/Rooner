package di

import data.KotlinLanguageSetting
import data.LinuxOsInformation
import data.StdoutLogger
import data.repositories.CodeRunnerRepositoryImpl
import data.repositories.TimeAnalyticsRepositoryImpl
import domain.LanguageSetting
import domain.Logger
import domain.OsInformation
import domain.repositories.CodeRunnerRepository
import domain.repositories.TimeAnalyticsRepository
import ui.RoonerViewModel

@Suppress("MemberVisibilityCanBePrivate")
object AppContainer {
    val logger: Logger = StdoutLogger()

    val timeAnalytics: TimeAnalyticsRepository = TimeAnalyticsRepositoryImpl(logger)

    val languageSetting: LanguageSetting = KotlinLanguageSetting()
    val osInformation: OsInformation = LinuxOsInformation()

    val repository: CodeRunnerRepository = CodeRunnerRepositoryImpl(
        languageSetting,
        osInformation
    )

    val viewModel: RoonerViewModel = RoonerViewModel(repository, languageSetting, timeAnalytics)
}