package pl.kuczabinski.weights.weight

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeightDao {

    @Insert
    fun insert(weightEntry: DBEntry): Long

    @Update
    fun update(weightEntry: DBEntry)
    @Delete
    fun delete(weightEntry: DBEntry)
    @Query("SELECT * FROM weight_entries ORDER BY id ASC")
    fun getAll(): List<DBEntry>

    @Query("SELECT * FROM weight_entries WHERE id = :id LIMIT 1")
    fun getById(id: Long): DBEntry?
}
