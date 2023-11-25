package domain.repositories

interface TimeAnalyticsRepository {
    fun recordTime(time: Long)
    fun getETA(): Long
}