package com.project.meuseventos.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.meuseventos.database.EventoDao
import com.project.meuseventos.database.UsuarioFirebaseDao
import com.project.meuseventos.model.Evento
import com.project.meuseventos.model.Usuario

class ListViewModel (
    private val eventoDao: EventoDao
) : ViewModel() {

    private val _eventos = MutableLiveData<List<Evento>>()
    val eventos: LiveData<List<Evento>> = _eventos

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    fun atualizarListaEventos() {

        eventoDao.all().addSnapshotListener { snapshot, error ->
            if (error != null)
                Log.i("ListViewModel", "${error.message}")
            else
                if (snapshot != null && !snapshot.isEmpty) {
                    val eventos =
                        snapshot.toObjects(Evento::class.java)
                    _eventos.value = eventos
                }
        }

    }

    fun encerrarSessao() {
        UsuarioFirebaseDao.encerrarSessao()
        _usuario.value = null
    }
}