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
    fun insert(weightEntry: WeightEntry): String

    @Update
    fun update(weightEntry: WeightEntry)
    @Delete
    fun delete(id: String)

    @Query("SELECT * FROM weight_entries ORDER BY id ASC")
    fun getAll(): List<WeightEntry>

    @Query("SELECT * FROM weight_entries WHERE id = :id")
    fun getById(id: Long)






}