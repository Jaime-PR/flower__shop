package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsCarritoModelo
import com.example.flower__shop.modelo.dataProductoItem
import com.example.flower__shop.vista.pantalla_detalle_producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleProductoPresentador(
    private val vista: pantalla_detalle_producto,
    private val producto: dataProductoItem,
    private val idCliente: Int = 1
) {

    private val modeloCarrito = clsCarritoModelo()

    fun iniciar() {
        vista.mostrarDetalleProducto(producto)
    }

    fun agregarAlCarrito(cantidad: Int = 1) {
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
}