package data

import domain.OsInformation
import java.nio.file.Path
import java.nio.file.Paths

class LinuxOsInformation : OsInformation {
    override fun getCachePath(): Path {
        return Paths.get(System.getenv("HOME"), ".cache")
    }
}