package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.flower__shop.R
import com.example.flower__shop.red.RetroFitCliente
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.presentador.clsDetalleProductoPresentador
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

    private var idClienteActual: Int = 1

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
        val producto = obtenerProductoDesdeIntent()

        presentador = clsDetalleProductoPresentador(this, producto, idClienteActual)
        configurarEventos()

        presentador.iniciar()
    }

    private fun obtenerIdCliente(): Int {
        val prefs = getSharedPreferences("flower_shop_prefs", MODE_PRIVATE)
        val idPrefs = prefs.getInt("id_cliente", -1)
        if (idPrefs > 0) {
            return idPrefs;
        }

        val idIntent = intent.getIntExtra("id_cliente", -1)
        if (idIntent > 0) {
            return idIntent
        }

        return 1
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
            presentador.agregarAlCarrito(1)
        }
    }

    fun mostrarDetalleProducto(producto: dataProductoItem) {
        //Formato del texto
        val precioFormateado = "$" + String.format("%.2f", producto.precio)
        var textoEstado = ""
        if (producto.disponible == true) {
            textoEstado = "Disponible"
        } else {
            textoEstado = "Agotado"
        }

        txtNombreProducto.text = producto.nombre
        txtPrecio.text = precioFormateado
        txtDisponibilidad.text = textoEstado
        txtDescripcion.text = "Arreglo floral: " + producto.nombre
        btnAgregarCarrito.text = "AGREGAR AL CARRITO · " + precioFormateado
        if (producto.disponible == true) {
            btnAgregarCarrito.isEnabled = true
        } else {
            btnAgregarCarrito.isEnabled = false
        }

        //Mostrar la imagen
        var urlImagen: String? = null

        if (producto.imagenUrl != null) {
            val ruta = producto.imagenUrl

            if (ruta.startsWith("http")) {
                urlImagen = ruta
            } else {
                urlImagen = RetroFitCliente.BASE_URL + ruta
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