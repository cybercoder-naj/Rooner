package domain

import java.nio.file.Path

interface OsInformation {
    fun getCachePath(): Path
}