package com.example.flower__shop.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataMotivo
import com.example.flower__shop.red.RetroFitCliente
import com.example.flower__shop.vista.MainActivity

class OcasionAdapter(
    private val activity: MainActivity,
    private var listaOcasiones: List<dataMotivo>,
    private val onOcasionClick: (dataMotivo) -> Unit
) : RecyclerView.Adapter<OcasionAdapter.OcasionViewHolder>() {

    class OcasionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgOcasion: ImageView =
            itemView.findViewById(R.id.imgOcasion)

        val txtNombreOcasion: TextView =
            itemView.findViewById(R.id.txtNombreOcasion)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OcasionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ocasion, parent, false)

        return OcasionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OcasionViewHolder, position: Int) {
        val ocasion = listaOcasiones[position]

        holder.txtNombreOcasion.text = ocasion.nombre

        val urlImagen = ocasion.imagenUrl?.let { ruta ->
            if (ruta.startsWith("http")) ruta else RetroFitCliente.BASE_URL + ruta
        }

        Glide.with(activity)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgOcasion)

        holder.itemView.setOnClickListener {
            onOcasionClick(ocasion)
        }
    }

    override fun getItemCount(): Int {
        return listaOcasiones.size
    }

    fun actualizarLista(nuevaLista: List<dataMotivo>) {
        listaOcasiones = nuevaLista
        notifyDataSetChanged()
    }
}