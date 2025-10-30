package com.llmhub.llmhub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llmhub.llmhub.repository.PersonaRepository

class PersonaViewModelFactory(private val repository: PersonaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
