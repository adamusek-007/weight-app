package pl.kuczabinski.weights.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WeightEntry::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun  weightDao(): WeightDao
}