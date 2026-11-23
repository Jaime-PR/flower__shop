package com.example.flower__shop.vista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.presentador.clsDetalleProductoPresentador
import com.example.flower__shop.red.RetroFitCliente
import com.google.android.material.button.MaterialButton

class pantalla_detalle_producto : AppCompatActivity() {

    private lateinit var presentador: clsDetalleProductoPresentador

    private lateinit var txtHeaderCarrito: TextView
    private lateinit var btnVolverCatalogo: TextView
    private lateinit var imgProducto: ImageView
    private lateinit var txtNombreProducto: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var txtDisponibilidad: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var btnAgregarCarrito: MaterialButton

    private lateinit var productoActual: dataProductoItem
    private var idClienteActual: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_detalle_producto)

        txtHeaderCarrito = findViewById(R.id.txtHeaderCarrito)
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo)
        imgProducto = findViewById(R.id.imgProducto)
        txtNombreProducto = findViewById(R.id.txtNombreProducto)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtDisponibilidad = findViewById(R.id.txtDisponibilidad)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito)

        idClienteActual = obtenerIdCliente()
        productoActual = obtenerProductoDesdeIntent()

        presentador = clsDetalleProductoPresentador(this, productoActual, idClienteActual)
        configurarEventos()

        presentador.iniciar()
    }

    override fun onResume() {
        super.onResume()
        val nuevoId = obtenerIdCliente()
        if (nuevoId != idClienteActual) {
            idClienteActual = nuevoId
            presentador = clsDetalleProductoPresentador(this, productoActual, idClienteActual)
        }
    }

    private fun sesionIniciada(): Boolean {
        val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
        return prefs.getBoolean("sesion_activa", false) && prefs.getInt("id_cliente", -1) > 0
    }

    private fun obtenerIdCliente(): Int {
        val prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE)
        val sesionActiva = prefs.getBoolean("sesion_activa", false)
        val idPrefs = prefs.getInt("id_cliente", -1)
        if (sesionActiva && idPrefs > 0) {
            return idPrefs
        }

        val idIntent = intent.getIntExtra("id_cliente", -1)
        if (idIntent > 0) {
            return idIntent
        }

        return -1
    }

    private fun obtenerProductoDesdeIntent(): dataProductoItem {
        return dataProductoItem(
            idProducto = intent.getIntExtra("id_producto", 0),
            nombre = intent.getStringExtra("nombre") ?: "",
            precio = intent.getDoubleExtra("precio", 0.0),
            imagenUrl = intent.getStringExtra("imagen_url"),
            disponible = intent.getBooleanExtra("disponible", false)
        )
    }

    private fun configurarEventos() {
        btnVolverCatalogo.setOnClickListener {
            finish()
        }

        txtHeaderCarrito.setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
        }

        btnAgregarCarrito.setOnClickListener {
            if (!sesionIniciada()) {
                Toast.makeText(this, "Inicia sesión para agregar productos al carrito.", Toast.LENGTH_SHORT).show()
                val intentLogin = Intent(this, iniciar_sesion_cliente::class.java)
                intentLogin.putExtra("volver_atras", true)
                startActivity(intentLogin)
                return@setOnClickListener
            }

            presentador.agregarAlCarrito(1)
        }
    }

    fun mostrarDetalleProducto(producto: dataProductoItem) {
        val precioFormateado = "$" + String.format("%.2f", producto.precio)
        val textoEstado = if (producto.disponible == true) "Disponible" else "Agotado"

        txtNombreProducto.text = producto.nombre
        txtPrecio.text = precioFormateado
        txtDisponibilidad.text = textoEstado
        txtDescripcion.text = "Arreglo floral: " + producto.nombre
        btnAgregarCarrito.text = "AGREGAR AL CARRITO · " + precioFormateado
        btnAgregarCarrito.isEnabled = producto.disponible == true

        var urlImagen: String? = null

        if (producto.imagenUrl != null) {
            val ruta = producto.imagenUrl

            urlImagen = if (ruta.startsWith("http")) {
                ruta
            } else {
                RetroFitCliente.BASE_URL + ruta
            }
        }

        Glide.with(this)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(imgProducto)
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}