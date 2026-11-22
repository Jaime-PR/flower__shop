package com.example.flower__shop.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.red.RetroFitCliente
import com.example.flower__shop.modelo.dataProductoCarrito
import com.example.flower__shop.vista.pantalla_carrito

class CarritoAdapter(
    private val activity: pantalla_carrito,
    private var listaCarrito: List<dataProductoCarrito>,
    private val onProductoClick: (dataProductoCarrito) -> Unit,
    private val onEliminarClick: (dataProductoCarrito) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCarritoProducto: ImageView = itemView.findViewById(R.id.ivCarritoProducto)
        val tvCarritoNombre: TextView = itemView.findViewById(R.id.tvCarritoNombre)
        val tvCarritoPrecio: TextView = itemView.findViewById(R.id.tvCarritoPrecio)
        val btnEliminarItem: ImageView = itemView.findViewById(R.id.btnEliminarItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito_producto, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = listaCarrito[position]

        holder.tvCarritoNombre.text = item.nombre

        val cantidadTexto = if (item.cantidad > 1) " (x${item.cantidad})" else ""
        holder.tvCarritoPrecio.text = "$${String.format("%.2f", item.importe)}$cantidadTexto"

        val urlImagen = item.imagenUrl?.let { ruta ->
            if (ruta.startsWith("http")) ruta else RetroFitCliente.BASE_URL + ruta
        }

        Glide.with(activity)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.ivCarritoProducto)

        holder.itemView.setOnClickListener {
            onProductoClick(item)
        }

        holder.btnEliminarItem.setOnClickListener {
            onEliminarClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listaCarrito.size
    }

    fun actualizarLista(nuevaLista: List<dataProductoCarrito>) {
        listaCarrito = nuevaLista
        notifyDataSetChanged()
    }
}