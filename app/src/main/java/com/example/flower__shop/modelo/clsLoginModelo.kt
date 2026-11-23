package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsLoginModelo {

    suspend fun iniciarSesion(correo: String, password: String): Response<dataLoginResponse> {
        val request = dataLoginRequest(
            correo = correo,
            password = password
        )
        return RetroFitCliente.apiService.login(request)
    }
}