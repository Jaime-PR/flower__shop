package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsPerfilEmpleadoModelo {

    suspend fun perfil_empleado(idEmpleado: Int): Response<dataPerfilEmpleadoResponse> {
        return RetroFitCliente.apiService.perfil_empleado(idEmpleado)
    }
}