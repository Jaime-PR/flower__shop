package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flower__shop.R
import com.example.flower__shop.adaptador.CarritoAdapter
import com.example.flower__shop.modelo.dataProductoCarrito
import com.example.flower__shop.modelo.dataResumenCarritoResponse
import com.example.flower__shop.presentador.clsCarritoPresentador
import com.google.android.material.button.MaterialButton

class pantalla_carrito : AppCompatActivity() {

    private lateinit var presentador: clsCarritoPresentador

    private lateinit var rvCarrito: RecyclerView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvEnvio: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnFinalizarCompra: Button

    private lateinit var navInicio: LinearLayout
    private lateinit var navExplorar: LinearLayout
    private lateinit var navCarrito: LinearLayout
    private lateinit var navPerfil: LinearLayout

    private lateinit var carritoAdapter: CarritoAdapter

    private var idClienteActual: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carrito)

        idClienteActual = obtenerIdCliente()

        rvCarrito = findViewById(R.id.rvCarrito)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvEnvio = findViewById(R.id.tvEnvio)
        tvTotal = findViewById(R.id.tvTotal)
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra)

        navInicio = findViewById(R.id.navInicio)
        navExplorar = findViewById(R.id.navExplorar)
        navCarrito = findViewById(R.id.navCarrito)
        navPerfil = findViewById(R.id.navPerfil)

        presentador = clsCarritoPresentador(this, idClienteActual)

        configurarRecyclerView()
        configurarEventos()

        presentador.iniciar()
    }

    override fun onResume() {
        super.onResume()
        if (::presentador.isInitialized) {
            presentador.cargarCarritoYResumen()
        }
    }

    private fun obtenerIdCliente(): Int {
        val prefs = getSharedPreferences("flower_shop_prefs", MODE_PRIVATE)
        val idPrefs = prefs.getInt("id_cliente", -1)
        if (idPrefs > 0) return idPrefs

        val idIntent = intent.getIntExtra("id_cliente", -1)
        if (idIntent > 0) return idIntent

        return 1
    }

    private fun configurarRecyclerView() {
        carritoAdapter = CarritoAdapter(
            activity = this,
            listaCarrito = emptyList(),
            onProductoClick = { producto ->
                Toast.makeText(
                    this,
                    "${producto.nombre} (x${producto.cantidad}): $${String.format("%.2f", producto.importe)}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onEliminarClick = { producto ->
                presentador.eliminarProducto(producto)
            }
        )
        rvCarrito.layoutManager = LinearLayoutManager(this)
        rvCarrito.adapter = carritoAdapter
    }

    private fun configurarEventos() {

        navInicio.setOnClickListener {
            // Navegar a Inicio cuando se conecte esa Activity
        }

        navExplorar.setOnClickListener {
            startActivity(Intent(this, pantalla_explorar::class.java))
            finish()
        }

        navCarrito.setOnClickListener {
            // Ya estás en pantalla_carrito
        }

        navPerfil.setOnClickListener {
            // Navegar a Perfil cuando se conecte esa Activity
        }
    }

    fun mostrarProductosCarrito(productos: List<dataProductoCarrito>) {
        carritoAdapter.actualizarLista(productos)
    }

    fun mostrarResumen(resumen: dataResumenCarritoResponse) {
        tvSubtotal.text = "$${String.format("%.2f", resumen.subtotal)}"
        tvEnvio.text = when {
            resumen.subtotal <= 0.0 -> "---"
            resumen.envioGratis || resumen.envio <= 0.0 -> "Gratis"
            else -> "$${String.format("%.2f", resumen.envio)}"
        }
        tvTotal.text = "$${String.format("%.2f", resumen.total)}"
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}