package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsCarritoModelo
import com.example.flower__shop.modelo.clsExplorarModelo
import com.example.flower__shop.modelo.dataMotivo
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.modelo.dataTipoFlor
import com.example.flower__shop.vista.pantalla_explorar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsExplorarPresentador(private val vista: pantalla_explorar, private val idCliente: Int = 0) {

    private val modeloExplorar = clsExplorarModelo()
    private val modeloCarrito = clsCarritoModelo()

    private var listaTiposFlor: List<dataTipoFlor> = emptyList()
    private var listaMotivos: List<dataMotivo> = emptyList()
    private var listaProductos: List<dataProductoItem> = emptyList()

    private var buscar: String? = null
    private var idTipoFlor: Int? = null
    private var idMotivo: Int? = null
    private var precioMin: Double? = null
    private var precioMax: Double? = null

    fun iniciar() {
        cargarTiposFlor()
        cargarMotivos()
        cargarProductos()
    }

    fun cargarTiposFlor() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaTiposFlor = modeloExplorar.obtenerTiposFlor()
                vista.mostrarTiposFlor(listaTiposFlor)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar tipos de flor: " + e.localizedMessage)
            }
        }
    }

    fun cargarMotivos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaMotivos = modeloExplorar.obtenerMotivos()
                vista.mostrarMotivos(listaMotivos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar motivos: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarTipoFlor(posicion: Int) {
        if (posicion >= 0 && posicion < listaTiposFlor.size) {
            val seleccionado = listaTiposFlor[posicion].idTipoFlor
            idTipoFlor = if (idTipoFlor == seleccionado) null else seleccionado
            cargarProductos()
        }
    }

    fun seleccionarMotivo(posicion: Int) {
        if (posicion >= 0 && posicion < listaMotivos.size) {
            val seleccionado = listaMotivos[posicion].idMotivo
            idMotivo = if (idMotivo == seleccionado) null else seleccionado
            cargarProductos()
        }
    }

    fun buscarPorTexto(texto: String) {
        val limpio = texto.trim()
        buscar = if (limpio.isEmpty()) null else limpio
        cargarProductos()
    }

    fun aplicarFiltros(minimo: Double?, maximo: Double?) {
        precioMin = minimo
        precioMax = maximo
        cargarProductos()
    }

    fun limpiarFiltros() {
        buscar = null
        idTipoFlor = null
        idMotivo = null
        precioMin = null
        precioMax = null
        cargarProductos()
    }

    fun cargarProductos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaProductos = modeloExplorar.obtenerProductos(
                    buscar = buscar,
                    idCategoria = null,
                    idMotivo = idMotivo,
                    idTipoFlor = idTipoFlor,
                    precioMin = precioMin,
                    precioMax = precioMax
                )
                vista.mostrarProductos(listaProductos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar productos: " + e.localizedMessage)
            }
        }
    }

    fun agregarProductoAlCarrito(producto: dataProductoItem, cantidad: Int = 1) {
        if (idCliente <= 0) {
            vista.mostrarError("No se encontró un cliente activo")
            return
        }

        if (!producto.disponible) {
            vista.mostrarError("Producto sin disponibilidad")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modeloCarrito.agregarAlCarrito(
                    idCliente = idCliente,
                    idProducto = producto.idProducto,
                    cantidad = cantidad
                )
                if (respuesta.exito) {
                    vista.mostrarMensaje(respuesta.mensaje)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al agregar al carrito: " + e.localizedMessage)
            }
        }
    }
    fun verDetalleProducto(producto: dataProductoItem) {
        vista.abrirDetalleProducto(producto.idProducto, idCliente)
    }

}