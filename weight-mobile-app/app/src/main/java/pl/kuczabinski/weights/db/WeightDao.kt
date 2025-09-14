package pl.kuczabinski.weights.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.kuczabinski.weights.Weight

@Dao


interface WeightDao {

    @Insert
    suspend fun insert(weightEntry: WeightEntry): String

    @Update
    suspend fun update(weightEntry: WeightEntry)
    @Delete
    suspend fun markAsDeleted(id: String)

    @Query("SELECT * FROM weight_entries ORDER BY id ASC")
    suspend fun getAll(): List<WeightEntry>

    @Query("SELECT * FROM weight_entries WHERE id = :id")
    suspend fun getById(id: Long)






}