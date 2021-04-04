package com.example.meuseventos.model

import com.google.firebase.firestore.DocumentId

class Evento  (
    val nome: String? = null,
    val data: String? = null,
    val bairro:String? = null,
    val autor:String? = null,
    @DocumentId
    val id: String? = null,
){
    override fun toString(): String = "$nome: $data"
}