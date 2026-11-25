package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsPerfilModelo {

    suspend fun perfil_cliente(idCliente: Int): Response<dataPerfilResponse> {
        return RetroFitCliente.apiService.perfil_cliente(idCliente)
    }
}