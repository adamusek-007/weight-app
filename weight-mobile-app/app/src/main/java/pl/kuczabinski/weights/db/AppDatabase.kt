package pl.kuczabinski.weights.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.kuczabinski.weights.weight.WeightDao

@Database(
    entities = [
        WeightDBEntry::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun  weightDao(): WeightDao
}