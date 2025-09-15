package pl.kuczabinski.weights.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries")
data class WeightDBEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val weight: Float
)