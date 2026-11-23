package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsRegistroModelo {

    suspend fun registrarCliente(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        correo: String,
        telefono: String,
        password: String
    ): Response<dataRegistroResponse> {
        val request = dataRegistroRequest(
            nombre = nombre,
            apellidoPaterno = apellidoPaterno,
            apellidoMaterno = apellidoMaterno,
            correo = correo,
            telefono = telefono,
            password = password
        )
        return RetroFitCliente.apiService.registrarCliente(request)
    }
}