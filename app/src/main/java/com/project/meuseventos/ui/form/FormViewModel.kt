package com.project.meuseventos.ui.form

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.meuseventos.database.AppUtil
import com.project.meuseventos.database.ConvidadoFirebaseDao
import com.project.meuseventos.database.EventoDao
import com.project.meuseventos.database.UsuarioFirebaseDao
import com.project.meuseventos.model.Convidado
import com.project.meuseventos.model.Evento
import com.project.meuseventos.model.Usuario

class FormViewModel(
    private val eventoDao: EventoDao,
    application: Application
) : AndroidViewModel(application) {

    private val app = application


    private val _eventos = MutableLiveData<Evento>()
    val eventos: LiveData<Evento> = _eventos

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String?>()
    val msg: LiveData<String?> = _msg

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    private val _convidados = MutableLiveData<List<Convidado>>()
    val convidados: LiveData<List<Convidado>> = _convidados


    init {
        _status.value = false
        _msg.value = null
        if (AppUtil.eventoSelecionado?.id == null){
            AppUtil.eventoSelecionado?.id == null

        }
        else{
            ConvidadoFirebaseDao(AppUtil!!.eventoSelecionado!!.id!!)
                .all()
                .addSnapshotListener { snapshot, error ->
                    if (error == null && snapshot != null && !snapshot.isEmpty)
                        _convidados.value = snapshot.toObjects(Convidado::class.java)
                }
        }

    }

    fun salvarEventos(nome:String, data:String,bairro:String,autor:String) {
        _status.value = false
        _msg.value = "Por favor, aguarde a persistĂȘncia!"

        val eventos = Evento(nome,data,bairro,autor)

        eventoDao.create(eventos)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "PersistĂȘncia realizada!"
            }
            .addOnFailureListener {
                _msg.value = "PersistĂȘncia falhou!"
                Log.e("EventoDaoFirebase", "${it.message}")
            }
    }

    fun deletarEvento(eventos: Evento) {
        _status.value = false
        _msg.value = "Por favor, aguarde a deleĂ§ĂŁo!"

        eventoDao.delete(eventos)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "DeleĂ§ĂŁo realizada!"
            }
            .addOnFailureListener {
                _msg.value = "deleĂ§ĂŁo falhou!"
            }
    }


    fun getUsuario(): String? {
        return UsuarioFirebaseDao.mAuth.currentUser.email
    }

    fun selectEvento(id: String) {
        eventoDao.read(id)
            .addOnSuccessListener {
                val eventos = it.toObject(Evento::class.java)
                if (eventos != null)
                    _eventos.value = eventos!!
                else
                    _msg.value = "O evento foi encontrada."
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }

    }
}