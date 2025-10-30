package com.llmhub.llmhub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llmhub.llmhub.data.PersonaEntity
import com.llmhub.llmhub.repository.PersonaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonaViewModel(private val repository: PersonaRepository) : ViewModel() {

    val allPersonas: StateFlow<List<PersonaEntity>> = repository.getAllPersonas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(persona: PersonaEntity) = viewModelScope.launch {
        repository.insert(persona)
    }

    fun update(persona: PersonaEntity) = viewModelScope.launch {
        repository.update(persona)
    }

    fun delete(persona: PersonaEntity) = viewModelScope.launch {
        repository.delete(persona)
    }
}
