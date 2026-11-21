package com.example.flower__shop.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataMotivo
import com.example.flower__shop.vista.pantalla_explorar

class MotivoAdapter(
    private val activity: pantalla_explorar,
    private var listaMotivos: List<dataMotivo>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MotivoAdapter.MotivoViewHolder>() {

    class MotivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMotivo: TextView = itemView.findViewById(R.id.tvMotivo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotivoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_motivo, parent, false)
        return MotivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MotivoViewHolder, position: Int) {
        val motivo = listaMotivos[position]
        holder.tvMotivo.text = motivo.nombre

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listaMotivos.size
    }

    fun actualizarLista(nuevaLista: List<dataMotivo>) {
        listaMotivos = nuevaLista
        notifyDataSetChanged()
    }
}