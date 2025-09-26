package pl.kuczabinski.weights.weight

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries")
data class DBEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val weight: Float
)
