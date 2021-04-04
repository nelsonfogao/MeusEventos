package com.example.meuseventos.database

import com.example.meuseventos.model.Evento
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface EventoDao {

    fun create(evento: Evento): Task<DocumentReference>
    fun read(id: String): Task<DocumentSnapshot>
    fun delete(evento: Evento): Task<Void>
    fun all(): CollectionReference
    fun allDocuments(): Task<QuerySnapshot>
}