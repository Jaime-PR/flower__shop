package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsPerfilModelo {

    suspend fun obtenerPerfil(idCliente: Int): Response<dataPerfilResponse> {
        return RetroFitCliente.apiService.obtenerPerfil(idCliente)
    }
}