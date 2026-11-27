package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsEnvioModelo {

    private val api = RetroFitCliente.apiService

    suspend fun registrarEnvio(request: dataEnvioRequest): Response<dataEnvioResponse> {
        return api.registrarEnvio(request)
    }

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

    suspend fun vaciarCarrito(idCliente: Int): dataAccionCarritoResponse {
        val resp = api.vaciarCarrito(dataVaciarCarritoRequest(idCliente))
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