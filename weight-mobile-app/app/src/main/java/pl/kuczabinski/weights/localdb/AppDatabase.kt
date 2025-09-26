package pl.kuczabinski.weights.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.kuczabinski.weights.weight.DBEntry
import pl.kuczabinski.weights.weight.WeightDao

@Database(
    entities = [
        DBEntry::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun  weightDao(): WeightDao
}