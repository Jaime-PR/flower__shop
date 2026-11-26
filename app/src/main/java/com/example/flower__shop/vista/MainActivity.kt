package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flower__shop.R
import com.example.flower__shop.adaptador.OcasionAdapter
import com.example.flower__shop.adaptador.ProductoDestacadoAdapter
import com.example.flower__shop.modelo.dataMotivo
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.presentador.clsInicioPresentador

class MainActivity : AppCompatActivity() {

    private lateinit var presentador: clsInicioPresentador

    private lateinit var rvOcasiones: RecyclerView
    private lateinit var rvProductosDestacados: RecyclerView

    private lateinit var ocasionAdapter: OcasionAdapter
    private lateinit var productoDestacadoAdapter: ProductoDestacadoAdapter

    private lateinit var navExplorar: LinearLayout
    private lateinit var navCarrito: LinearLayout
    private lateinit var navPerfil: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvOcasiones = findViewById(R.id.rvOcasiones)
        rvProductosDestacados = findViewById(R.id.rvProductosDestacados)

        navExplorar = findViewById(R.id.navExplorar)
        navCarrito = findViewById(R.id.navCarrito)
        navPerfil = findViewById(R.id.navPerfil)

        presentador = clsInicioPresentador(this)

        configurarRecyclerViews()
        configurarEventos()

        presentador.iniciar()
    }

    private fun configurarRecyclerViews() {
        ocasionAdapter = OcasionAdapter(
            activity = this,
            listaOcasiones = emptyList(),
            onOcasionClick = { ocasion ->
                mostrarMensaje("Ocasión seleccionada: ${ocasion.nombre}")
            }
        )

        rvOcasiones.layoutManager = GridLayoutManager(this, 2)
        rvOcasiones.adapter = ocasionAdapter

        productoDestacadoAdapter = ProductoDestacadoAdapter(
            activity = this,
            listaProductos = emptyList(),
            onProductoClick = { producto ->
                mostrarMensaje("Producto seleccionado: ${producto.nombre}")
            },
            onAgregarCarritoClick = { producto ->
                mostrarMensaje(
                    "Integración del carrito pendiente para ${producto.nombre}"
                )
            }
        )

        rvProductosDestacados.layoutManager = LinearLayoutManager(this)
        rvProductosDestacados.adapter = productoDestacadoAdapter
    }

    private fun configurarEventos() {
        navExplorar.setOnClickListener {
            startActivity(Intent(this, pantalla_explorar::class.java))
        }

        navCarrito.setOnClickListener {
            startActivity(Intent(this, pantalla_carrito::class.java))
        }

        navPerfil.setOnClickListener {
            startActivity(Intent(this, pantalla_perfil::class.java))
        }
    }

    fun mostrarOcasiones(ocasiones: List<dataMotivo>) {
        ocasionAdapter.actualizarLista(ocasiones)
    }

    fun mostrarProductosDestacados(productos: List<dataProductoItem>) {
        productoDestacadoAdapter.actualizarLista(productos)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}