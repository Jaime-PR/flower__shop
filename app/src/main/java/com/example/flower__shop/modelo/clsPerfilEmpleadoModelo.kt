package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente
import retrofit2.Response

class clsPerfilEmpleadoModelo {

    suspend fun perfil_empleado(idEmpleado: Int, correo: String): Response<dataPerfilEmpleadoResponse> {
        val request = dataPerfilEmpleadoRequest(id = idEmpleado, correo = correo)
        return RetroFitCliente.apiService.perfil_empleado(request)
    }
}