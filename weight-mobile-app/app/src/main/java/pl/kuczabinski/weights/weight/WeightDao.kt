package pl.kuczabinski.weights.weight

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import pl.kuczabinski.weights.db.WeightDBEntry

@Dao
interface WeightDao {

    @Insert
    suspend fun insert(weightEntry: WeightDBEntry): String

    @Update
    suspend fun update(weightEntry: WeightDBEntry)

    @Delete
    suspend fun markAsDeleted(id: String)

    @Query("SELECT * FROM weight_entries ORDER BY id ASC")
    suspend fun getAll(): List<WeightDBEntry>

    @Query("SELECT * FROM weight_entries WHERE id = :id")
    suspend fun getById(id: Long)






}