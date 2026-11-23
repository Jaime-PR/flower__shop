package com.example.flower__shop.vista

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flower__shop.R
import com.example.flower__shop.adaptador.MotivoAdapter
import com.example.flower__shop.adaptador.ProductoCatalogoAdapter
import com.example.flower__shop.adaptador.TipoFlorAdapter
import com.example.flower__shop.modelo.dataMotivo
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.modelo.dataTipoFlor
import com.example.flower__shop.presentador.clsExplorarPresentador

class pantalla_explorar : AppCompatActivity() {

    private lateinit var presentador: clsExplorarPresentador

    private lateinit var etBuscar: EditText
    private lateinit var tvFiltros: TextView
    private lateinit var rvTiposFlor: RecyclerView
    private lateinit var rvMotivos: RecyclerView
    private lateinit var rvProductos: RecyclerView

    private lateinit var navInicio: LinearLayout
    private lateinit var navExplorar: LinearLayout
    private lateinit var navCarrito: LinearLayout
    private lateinit var navPerfil: LinearLayout

    private lateinit var tipoFlorAdapter: TipoFlorAdapter
    private lateinit var motivoAdapter: MotivoAdapter
    private lateinit var productoAdapter: ProductoCatalogoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_explorar)

        etBuscar = findViewById(R.id.etBuscar)
        tvFiltros = findViewById(R.id.tvFiltros)
        rvTiposFlor = findViewById(R.id.rvTiposFlor)
        rvMotivos = findViewById(R.id.rvMotivos)
        rvProductos = findViewById(R.id.rvProductos)

        navInicio = findViewById(R.id.navInicio)
        navExplorar = findViewById(R.id.navExplorar)
        navCarrito = findViewById(R.id.navCarrito)
        navPerfil = findViewById(R.id.navPerfil)

        presentador = clsExplorarPresentador(this)

        configurarRecyclerViews()
        configurarEventos()

        presentador.iniciar()
    }

    private fun configurarRecyclerViews() {
        tipoFlorAdapter = TipoFlorAdapter(this, emptyList()) { posicion ->
            presentador.seleccionarTipoFlor(posicion)
        }
        rvTiposFlor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTiposFlor.adapter = tipoFlorAdapter

        motivoAdapter = MotivoAdapter(this, emptyList()) { posicion ->
            presentador.seleccionarMotivo(posicion)
        }
        rvMotivos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvMotivos.adapter = motivoAdapter

        productoAdapter = ProductoCatalogoAdapter(
            activity = this,
            listaProductos = emptyList(),
            onProductoClick = { producto ->
                Toast.makeText(this, "Producto: ${producto.nombre}", Toast.LENGTH_SHORT).show()
            },
            onAgregarCarritoClick = { producto ->
                presentador.agregarProductoAlCarrito(producto, 1)
            }
        )
        rvProductos.layoutManager = GridLayoutManager(this, 2)
        rvProductos.adapter = productoAdapter
    }

    private fun configurarEventos() {
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presentador.buscarPorTexto(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        tvFiltros.setOnClickListener {
            mostrarDialogoFiltros()
        }

        navInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
            startActivity(intent)
        }

        navCarrito.setOnClickListener {
            val intent = Intent(this, pantalla_carrito::class.java)
            startActivity(intent)
        }

        navPerfil.setOnClickListener {
            startActivity(Intent(this, pantalla_perfil::class.java))
        }
    }

    private fun mostrarDialogoFiltros() {
        val opciones = arrayOf(
            "Hasta $350",
            "$350 - $500",
            "Más de $500",
            "Limpiar filtros"
        )

        AlertDialog.Builder(this)
            .setTitle("Filtrar por precio")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> presentador.aplicarFiltros(null, 350.0)
                    1 -> presentador.aplicarFiltros(350.0, 500.0)
                    2 -> presentador.aplicarFiltros(500.0, null)
                    3 -> {
                        etBuscar.text.clear()
                        presentador.limpiarFiltros()
                    }
                }
            }
            .show()
    }

    fun mostrarTiposFlor(tipos: List<dataTipoFlor>) {
        tipoFlorAdapter.actualizarLista(tipos)
    }

    fun mostrarMotivos(motivos: List<dataMotivo>) {
        motivoAdapter.actualizarLista(motivos)
    }

    fun mostrarProductos(productos: List<dataProductoItem>) {
        productoAdapter.actualizarLista(productos)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}