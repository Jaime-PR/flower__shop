package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsPerfilModelo {

    suspend fun perfil_cliente(idCliente: Int, correo: String): Response<dataPerfilResponse> {
        val request = dataPerfilRequest(id = idCliente, correo = correo)
        return RetroFitCliente.apiService.perfil_cliente(request)
    }
}