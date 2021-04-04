package com.project.meuseventos.ui.form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.meuseventos.database.EventoDao

class FormEventosViewModelFactory (
    val eventoDao: EventoDao,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)){
            return FormViewModel(eventoDao, application) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }

}