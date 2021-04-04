package com.example.meuseventos.ui.form

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meuseventos.database.AppUtil
import com.example.meuseventos.database.ConvidadoFirebaseDao
import com.example.meuseventos.database.EventoDao
import com.example.meuseventos.database.UsuarioFirebaseDao
import com.example.meuseventos.model.Convidado
import com.example.meuseventos.model.Evento
import com.example.meuseventos.model.Usuario

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
        _msg.value = "Por favor, aguarde a persistência!"

        val eventos = Evento(nome,data,bairro,autor)

        eventoDao.create(eventos)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Persistência realizada!"
            }
            .addOnFailureListener {
                _msg.value = "Persistência falhou!"
                Log.e("EventoDaoFirebase", "${it.message}")
            }
    }

    fun deletarEvento(eventos: Evento) {
        _status.value = false
        _msg.value = "Por favor, aguarde a deleção!"

        eventoDao.delete(eventos)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Deleção realizada!"
            }
            .addOnFailureListener {
                _msg.value = "deleção falhou!"
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