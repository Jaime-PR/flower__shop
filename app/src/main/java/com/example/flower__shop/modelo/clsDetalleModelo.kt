package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente

class clsDetalleModelo {

    private val api = RetroFitCliente.apiService

    suspend fun obtenerDetalleProducto(idProducto: Int): dataDetalleProductoResponse {
        return try {
            val resp = api.obtenerDetalleProducto(idProducto)
            if (resp.isSuccessful) {
                resp.body() ?: dataDetalleProductoResponse(
                    exito = false,
                    mensaje = "Respuesta vacía del servidor"
                )
            } else {
                dataDetalleProductoResponse(
                    exito = false,
                    mensaje = "Error HTTP ${resp.code()}"
                )
            }
        } catch (e: Exception) {
            dataDetalleProductoResponse(
                exito = false,
                mensaje = "Error de conexión: ${e.localizedMessage}"
            )
        }
    }
}