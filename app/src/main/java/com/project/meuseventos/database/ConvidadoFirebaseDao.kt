package com.project.meuseventos.database

import com.project.meuseventos.model.Convidado
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ConvidadoFirebaseDao (id: String) {

    private val collection =
        FirebaseFirestore
            .getInstance()
            .collection("eventos")
            .document(id)
            .collection("convidados")

    fun create(convidado: Convidado): Task<DocumentReference> {
        return collection.add(convidado)
    }

    fun all(): CollectionReference {
        return collection
    }

}