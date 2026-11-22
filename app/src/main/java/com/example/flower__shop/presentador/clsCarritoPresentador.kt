package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsCarritoModelo
import com.example.flower__shop.modelo.dataProductoCarrito
import com.example.flower__shop.vista.pantalla_carrito
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCarritoPresentador(
    private val vista: pantalla_carrito,
    private val idCliente: Int
) {

    private val modeloCarrito = clsCarritoModelo()
    private var listaCarrito: List<dataProductoCarrito> = emptyList()

    fun iniciar() {
        cargarCarritoYResumen()
    }

    fun cargarCarritoYResumen() {
        if (idCliente <= 0) {
            vista.mostrarError("No se encontró un cliente activo")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuestaCarrito = modeloCarrito.obtenerCarrito(idCliente)
                if (respuestaCarrito.exito) {
                    listaCarrito = respuestaCarrito.productos
                    vista.mostrarProductosCarrito(listaCarrito)
                } else {
                    vista.mostrarError(respuestaCarrito.mensaje ?: "No se pudo cargar el carrito")
                }

                val respuestaResumen = modeloCarrito.obtenerResumenCarrito(idCliente)
                if (respuestaResumen.exito) {
                    vista.mostrarResumen(respuestaResumen)
                } else {
                    vista.mostrarError(respuestaResumen.mensaje ?: "No se pudo obtener el resumen")
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar carrito: " + e.localizedMessage)
            }
        }
    }

    fun eliminarProducto(producto: dataProductoCarrito) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modeloCarrito.eliminarDelCarrito(idCliente, producto.idProducto)
                if (respuesta.exito) {
                    vista.mostrarMensaje(respuesta.mensaje)
                    cargarCarritoYResumen()
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al eliminar producto: " + e.localizedMessage)
            }
        }
    }
}