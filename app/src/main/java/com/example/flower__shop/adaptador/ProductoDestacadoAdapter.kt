package com.example.flower__shop.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.red.RetroFitCliente
import com.example.flower__shop.vista.MainActivity

class ProductoDestacadoAdapter(
    private val activity: MainActivity,
    private var listaProductos: List<dataProductoItem>,
    private val onProductoClick: (dataProductoItem) -> Unit,
    private val onAgregarCarritoClick: (dataProductoItem) -> Unit
) : RecyclerView.Adapter<ProductoDestacadoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProductoDestacado: ImageView =
            itemView.findViewById(R.id.imgProductoDestacado)

        val txtNombreProductoDestacado: TextView =
            itemView.findViewById(R.id.txtNombreProductoDestacado)

        val txtPrecioProductoDestacado: TextView =
            itemView.findViewById(R.id.txtPrecioProductoDestacado)

        val btnAgregarCarrito: AppCompatImageButton =
            itemView.findViewById(R.id.btnAgregarCarrito)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_destacado, parent, false)

        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.txtNombreProductoDestacado.text = producto.nombre
        holder.txtPrecioProductoDestacado.text =
            "$${String.format("%.2f", producto.precio)}"

        val urlImagen = producto.imagenUrl?.let { ruta ->
            if (ruta.startsWith("http")) ruta else RetroFitCliente.BASE_URL + ruta
        }

        Glide.with(activity)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgProductoDestacado)

        holder.btnAgregarCarrito.isEnabled = producto.disponible
        holder.btnAgregarCarrito.alpha =
            if (producto.disponible) 1.0f else 0.5f

        holder.itemView.setOnClickListener {
            onProductoClick(producto)
        }

        holder.btnAgregarCarrito.setOnClickListener {
            onAgregarCarritoClick(producto)
        }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    fun actualizarLista(nuevaLista: List<dataProductoItem>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}