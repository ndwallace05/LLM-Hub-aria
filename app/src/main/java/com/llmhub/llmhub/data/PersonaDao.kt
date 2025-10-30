package com.llmhub.llmhub.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    @Query("SELECT * FROM personas ORDER BY name ASC")
    fun getAllPersonas(): Flow<List<PersonaEntity>>

    @Query("SELECT * FROM personas WHERE id = :id")
    suspend fun getPersonaById(id: Int): PersonaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(persona: PersonaEntity)

    @Update
    suspend fun update(persona: PersonaEntity)

    @Delete
    suspend fun delete(persona: PersonaEntity)
}
