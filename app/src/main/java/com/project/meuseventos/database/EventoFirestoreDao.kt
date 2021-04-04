package com.project.meuseventos.database

import com.project.meuseventos.model.Evento
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

class EventoFirestoreDao: EventoDao {

    private val collection = FirebaseFirestore
        .getInstance()
        .collection("eventos")



    override fun create(evento: Evento): Task<DocumentReference> {
        return collection
            .add(evento)
    }


    override fun read(id: String): Task<DocumentSnapshot> {
        return collection
            .document(id)
            .get()
    }
    override fun delete(evento: Evento): Task<Void> {
        return collection
            .document(evento.id!!)
            .delete()
    }

    override fun all(): CollectionReference {
        return collection
    }

    override fun allDocuments(): Task<QuerySnapshot> {
        return collection.get()
    }

}