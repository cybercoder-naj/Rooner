package data.repositories

import domain.Logger
import domain.repositories.TimeAnalyticsRepository

class TimeAnalyticsRepositoryImpl(
    private val logger: Logger
) : TimeAnalyticsRepository {
    private val times = mutableListOf<Long>()

    companion object {
        private const val NUM_TIMES = 10
    }

    override fun recordTime(time: Long) {
        logger.log("Successfully logged time: $time ms.")
        times.add(time)
    }

    override fun getETA(): Long {
        var n: Int
        var decayRate: Double
        return times
            .takeLast(NUM_TIMES)
            .also {
                n = it.size
                decayRate = 1.0 / n
            }
            .mapIndexed { index, time ->
                time * decayRate * (index + 1) // decayRate * (lastIndex + 1) = 1.0
            }
            .fold(0.0, Double::plus)
            .let { it / n }
            .also { logger.log("Calculated ETA: $it ms") }
            .toLong()
    }
}