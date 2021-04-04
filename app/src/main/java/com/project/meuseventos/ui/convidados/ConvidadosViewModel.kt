package com.project.meuseventos.ui.convidados

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.meuseventos.database.AppUtil
import com.project.meuseventos.database.ConvidadoFirebaseDao
import com.project.meuseventos.model.Convidado

class ConvidadosViewModel : ViewModel() {

    private val _convidados = MutableLiveData<List<Convidado>>()
    val convidados: LiveData<List<Convidado>> = _convidados

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String?>()
    val msg: LiveData<String?> = _msg

    init {
        _status.value = false
        _msg.value = null
    }


    fun save(texto: String) {

        _status.value = false
        _msg.value = "Por favor, aguarde a persistência!"


        ConvidadoFirebaseDao(AppUtil!!.eventoSelecionado!!.id!!)
            .create(Convidado(texto))
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Persistência realizada!"
            }
            .addOnFailureListener {
                _msg.value = "Persistência falhou!"
                Log.e("EventoDaoFirebase", "${it.message}")
            }
    }
}