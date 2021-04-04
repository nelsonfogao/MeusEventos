package com.project.meuseventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.meuseventos.model.Evento
import kotlinx.android.synthetic.main.recycler_list_eventos.view.*

class RecyclerAdapter (
    private val eventos: List<Evento>,
    val actionClick: (Evento) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.EventosViewHolder>() {

    class EventosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val textNome: TextView = itemView.textViewNomeEvento
        val textData: TextView = itemView.textViewDataEvento
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_list_eventos,
                parent,
                false
            )
        return EventosViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventosViewHolder, position: Int) {
        val evento = eventos[position]

        holder.textNome.text = evento.nome
        holder.textData.text = evento.data


        holder.itemView.setOnClickListener {
            actionClick(evento)
        }

    }

    override fun getItemCount(): Int = eventos.size
}