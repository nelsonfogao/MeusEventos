package com.example.meuseventos.ui.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meuseventos.database.UsuarioFirebaseDao
import com.google.firebase.auth.FirebaseUser

class CadastroViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status
    private var mUser: FirebaseUser? = null
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun salvarCadastro(email: String, senha: String,
                       nome: String) {
        UsuarioFirebaseDao
            .cadastrarCredenciais(email, senha) // createUserWithEmailAndPassword
            .addOnSuccessListener {
                val uid = it.user!!.uid         // firebaseUser.uid
                UsuarioFirebaseDao
                    .cadastrarPerfil(uid, nome)   // collection("usuarios").document(uid)
                    .addOnSuccessListener {
                        _status.value = true
                        mUser = UsuarioFirebaseDao.mAuth.currentUser
                        _msg.value = "Usuário cadastrado com sucesso!"
                    }
                    .addOnFailureListener {
                        _msg.value = "${it.message}"
                    }
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }
    }
}