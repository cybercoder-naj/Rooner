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
        var nfactor: Long
        return times
            .takeLast(NUM_TIMES)
            .also {
                nfactor = it.last()
                n = it.size
                decayRate = 1.0 / n
            }
            .map { it.toDouble() / nfactor }
            .mapIndexed { index, nTime ->
                nTime * decayRate * (index + 1) // decayRate * (lastIndex + 1) = 1.0
            }
            .fold(0.0, Double::plus)
            .let { it / n * nfactor}
            .also { logger.log("Calculated ETA: $it ms") }
            .toLong()
    }
}