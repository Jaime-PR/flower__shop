package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente

class clsCarritoModelo {

    private val api = RetroFitCliente.apiService

    suspend fun obtenerCarrito(idCliente: Int): dataObtenerCarritoResponse {
        val resp = api.obtenerCarrito(idCliente)
        return if (resp.isSuccessful) {
            resp.body() ?: dataObtenerCarritoResponse(
                exito = false,
                mensaje = "Respuesta vacía del servidor"
            )
        } else {
            dataObtenerCarritoResponse(
                exito = false,
                mensaje = "Error HTTP ${resp.code()}"
            )
        }
    }

    suspend fun obtenerResumenCarrito(idCliente: Int): dataResumenCarritoResponse {
        val resp = api.obtenerResumenCarrito(idCliente)
        return if (resp.isSuccessful) {
            resp.body() ?: dataResumenCarritoResponse(
                exito = false,
                mensaje = "Respuesta vacía del servidor"
            )
        } else {
            dataResumenCarritoResponse(
                exito = false,
                mensaje = "Error HTTP ${resp.code()}"
            )
        }
    }

    suspend fun agregarAlCarrito(
        idCliente: Int,
        idProducto: Int,
        cantidad: Int = 1
    ): dataAccionCarritoResponse {
        val resp = api.agregarAlCarrito(
            dataAgregarCarritoRequest(
                idCliente = idCliente,
                idProducto = idProducto,
                cantidad = cantidad
            )
        )
        val body = resp.body()
        return if (resp.isSuccessful && body != null) {
            dataAccionCarritoResponse(
                exito = body.exito,
                mensaje = body.mensaje,
                totalArticulos = body.totalArticulos,
                subtotal = body.subtotal
            )
        } else {
            dataAccionCarritoResponse(
                exito = false,
                mensaje = body?.mensaje ?: "Error HTTP ${resp.code()}"
            )
        }
    }

    suspend fun eliminarDelCarrito(idCliente: Int, idProducto: Int): dataAccionCarritoResponse {
        val resp = api.eliminarDelCarrito(
            dataEliminarCarritoRequest(
                idCliente = idCliente,
                idProducto = idProducto
            )
        )
        val body = resp.body()
        return if (resp.isSuccessful && body != null) {
            dataAccionCarritoResponse(
                exito = body.exito,
                mensaje = body.mensaje,
                totalArticulos = body.totalArticulos,
                subtotal = body.subtotal
            )
        } else {
            dataAccionCarritoResponse(
                exito = false,
                mensaje = body?.mensaje ?: "Error HTTP ${resp.code()}"
            )
        }
    }
}