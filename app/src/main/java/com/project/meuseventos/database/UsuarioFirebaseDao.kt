package com.project.meuseventos.database

import com.project.meuseventos.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object UsuarioFirebaseDao {

    val mAuth = FirebaseAuth.getInstance()
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("usuarios")


    fun cadastrarCredenciais(email: String, senha: String): Task<AuthResult> {
        return mAuth
            .createUserWithEmailAndPassword(email, senha)
    }

    fun cadastrarPerfil(uid: String, nome: String): Task<Void> {
        return collection
            .document(uid)
            .set(Usuario(nome))
    }


    fun verificarCredenciais(email: String, senha: String): Task<AuthResult> {
        return mAuth
            .signInWithEmailAndPassword(email, senha)
    }

    fun encerrarSessao() {
        mAuth.signOut()
    }

}