package com.llmhub.llmhub.repository

import com.llmhub.llmhub.data.PersonaDao
import com.llmhub.llmhub.data.PersonaEntity
import kotlinx.coroutines.flow.Flow

class PersonaRepository(private val personaDao: PersonaDao) {

    fun getAllPersonas(): Flow<List<PersonaEntity>> = personaDao.getAllPersonas()

    suspend fun getPersonaById(id: Int): PersonaEntity? {
        return personaDao.getPersonaById(id)
    }

    suspend fun insert(persona: PersonaEntity) {
        personaDao.insert(persona)
    }

    suspend fun update(persona: PersonaEntity) {
        personaDao.update(persona)
    }

    suspend fun delete(persona: PersonaEntity) {
        personaDao.delete(persona)
    }
}
