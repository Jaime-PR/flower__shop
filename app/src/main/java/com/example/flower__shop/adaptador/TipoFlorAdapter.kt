package com.example.flower__shop.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataTipoFlor
import com.example.flower__shop.red.RetroFitCliente
import com.example.flower__shop.vista.pantalla_explorar

class TipoFlorAdapter(
    private val activity: pantalla_explorar,
    private var listaTiposFlor: List<dataTipoFlor>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<TipoFlorAdapter.TipoFlorViewHolder>() {

    class TipoFlorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTipoFlor: ImageView = itemView.findViewById(R.id.ivTipoFlor)
        val tvNombreTipoFlor: TextView = itemView.findViewById(R.id.tvNombreTipoFlor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoFlorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tipo_flor, parent, false)
        return TipoFlorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipoFlorViewHolder, position: Int) {
        val tipoFlor = listaTiposFlor[position]
        holder.tvNombreTipoFlor.text = tipoFlor.nombre

        val urlImagenCompleta = tipoFlor.imagenUrl?.let { ruta ->
            if (ruta.startsWith("http")) ruta else RetroFitCliente.BASE_URL + ruta
        }

        Glide.with(holder.itemView.context)
            .load(urlImagenCompleta)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.ivTipoFlor)

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listaTiposFlor.size
    }

    fun actualizarLista(nuevaLista: List<dataTipoFlor>) {
        listaTiposFlor = nuevaLista
        notifyDataSetChanged()
    }
}