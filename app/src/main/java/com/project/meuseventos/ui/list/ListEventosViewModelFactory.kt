package com.project.meuseventos.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.meuseventos.database.EventoDao

class ListEventosViewModelFactory (
    private val eventoDao: EventoDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(eventoDao) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}