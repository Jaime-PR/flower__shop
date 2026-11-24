package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsCarritoModelo
import com.example.flower__shop.modelo.clsDetalleModelo
import com.example.flower__shop.modelo.dataDetalleProductoResponse
import com.example.flower__shop.vista.pantalla_detalle_producto // Cambia al nombre exacto de tu Activity/Fragment de detalle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleProductoPresentador(
    private val vista: pantalla_detalle_producto, // Cambia al nombre exacto de tu vista
    private val idProducto: Int,
    private val idCliente: Int = 0
) {

    private val modeloDetalle = clsDetalleModelo()
    private val modeloCarrito = clsCarritoModelo()
    private var productoActual: dataDetalleProductoResponse? = null

    fun iniciar() {
        cargarDetalleProducto()
    }

    fun cargarDetalleProducto() {
        if (idProducto <= 0) {
            vista.mostrarError("No se encontró un producto válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modeloDetalle.obtenerDetalleProducto(idProducto)
                if (respuesta.exito == true || respuesta.idProducto > 0) {
                    productoActual = respuesta
                    vista.mostrarDetalleProducto(respuesta)
                } else {
                    vista.mostrarError(respuesta.mensaje ?: "No se pudo cargar el detalle del producto")
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar detalle: " + e.localizedMessage)
            }
        }
    }

    fun agregarProductoAlCarrito(cantidad: Int = 1) {
        if (idCliente <= 0) {
            vista.mostrarError("No se encontró un cliente activo")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modeloCarrito.agregarAlCarrito(idCliente, idProducto, cantidad)
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
}