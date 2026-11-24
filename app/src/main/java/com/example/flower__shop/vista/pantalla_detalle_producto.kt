package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataDetalleProductoResponse
import com.example.flower__shop.presentador.clsDetalleProductoPresentador
import com.example.flower__shop.red.RetroFitCliente
import com.google.android.material.button.MaterialButton

class pantalla_detalle_producto : AppCompatActivity() {

    private lateinit var presentador: clsDetalleProductoPresentador

    private lateinit var txtHeaderCarrito: TextView
    private lateinit var btnVolverCatalogo: TextView
    private lateinit var imgProductoDestacado: ImageView
    private lateinit var txtNombreProducto: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var txtDisponibilidad: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var btnAgregarCarrito: MaterialButton

    private lateinit var navInicio: LinearLayout
    private lateinit var navExplorar: LinearLayout
    private lateinit var navCarrito: LinearLayout
    private lateinit var navPerfil: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_detalle_producto)

        txtHeaderCarrito = findViewById(R.id.txtHeaderCarrito)
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo)
        imgProductoDestacado = findViewById(R.id.imgProductoDestacado)
        txtNombreProducto = findViewById(R.id.txtNombreProducto)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtDisponibilidad = findViewById(R.id.txtDisponibilidad)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito)

        navInicio = findViewById(R.id.navInicio)
        navExplorar = findViewById(R.id.navExplorar)
        navCarrito = findViewById(R.id.navCarrito)
        navPerfil = findViewById(R.id.navPerfil)

        val idProducto = intent.getIntExtra("id_producto", 0)
        val idCliente = intent.getIntExtra("id_cliente", 0)

        presentador = clsDetalleProductoPresentador(this, idProducto, idCliente)
        presentador.iniciar()

        btnVolverCatalogo.setOnClickListener {
            finish()
        }

        btnAgregarCarrito.setOnClickListener {
            presentador.agregarProductoAlCarrito(cantidad = 1)
        }

        txtHeaderCarrito.setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
        }

        navInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        navExplorar.setOnClickListener {
            finish()
        }

        navCarrito.setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
        }

        navPerfil.setOnClickListener {
            startActivity(Intent(this, pantalla_perfil::class.java))
        }
    }

    // AQUÍ ESTÁ EL MÉTODO CON LA LÓGICA DE RETROFIT BASE_URL Y GLIDE:
    fun mostrarDetalleProducto(producto: dataDetalleProductoResponse) {
        txtNombreProducto.text = producto.nombre
        txtPrecio.text = "$${String.format("%.2f", producto.precio)}"
        txtDescripcion.text = producto.descripcion ?: "Sin descripción disponible"

        if (producto.disponible) {
            txtDisponibilidad.text = "Disponible"
            btnAgregarCarrito.isEnabled = true
            btnAgregarCarrito.text = "AGREGAR AL CARRITO · $${String.format("%.2f", producto.precio)}"
        } else {
            txtDisponibilidad.text = "Agotado"
            btnAgregarCarrito.isEnabled = false
            btnAgregarCarrito.text = "PRODUCTO AGOTADO"
        }

        val urlImagenCompleta = producto.imagenUrl?.let { ruta ->
            if (ruta.startsWith("http")) ruta else RetroFitCliente.BASE_URL + ruta
        }

        if (!urlImagenCompleta.isNullOrEmpty()) {
            Glide.with(this)
                .load(urlImagenCompleta)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(imgProductoDestacado)
        }
    }

    fun mostrarMensaje(mensaje: String?) {
        Toast.makeText(this, mensaje ?: "Operación exitosa", Toast.LENGTH_SHORT).show()
    }

    fun mostrarError(mensaje: String?) {
        Toast.makeText(this, mensaje ?: "Ocurrió un error", Toast.LENGTH_SHORT).show()
    }
}