package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsCarritoModelo
import com.example.flower__shop.modelo.dataProductoCarrito
import com.example.flower__shop.modelo.dataResumenCarritoResponse
import com.example.flower__shop.vista.pantalla_carrito
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCarritoPresentador(
    private var vista: pantalla_carrito,
    private var idCliente: Int
) {

    private val modeloCarrito = clsCarritoModelo()
    private var listaCarrito: List<dataProductoCarrito> = emptyList()

    fun actualizarCliente(nuevoId: Int) {
        idCliente = nuevoId
    }

    fun cargarCarritoYResumen() {
        if (idCliente <= 0) {
            listaCarrito = emptyList()
            vista.mostrarProductosCarrito(emptyList())
            vista.mostrarResumen(
                dataResumenCarritoResponse(
                    exito = true,
                    subtotal = 0.0,
                    envio = 0.0,
                    total = 0.0,
                    envioGratis = false
                )
            )
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
        if (idCliente <= 0) {
            vista.mostrarError("Inicia sesión para modificar el carrito")
            return
        }

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