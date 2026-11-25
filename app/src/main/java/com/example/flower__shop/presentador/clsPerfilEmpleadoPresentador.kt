package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsPerfilEmpleadoModelo
import com.example.flower__shop.vista.pantalla_perfil_empleado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPerfilEmpleadoPresentador(
    private val vista: pantalla_perfil_empleado,
    private val idEmpleado: Int
) {

    private val modelo = clsPerfilEmpleadoModelo()

    fun iniciar() {
        cargarPerfil()
    }

    fun cargarPerfil() {
        if (idEmpleado <= 0) {
            vista.mostrarError("No se encontró un empleado activo")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = modelo.perfil_empleado(idEmpleado)
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    if (respuesta != null && respuesta.exito) {
                        val nombreCompleto = listOfNotNull(
                            respuesta.nombre?.takeIf { it.isNotBlank() },
                            respuesta.apellidoPaterno?.takeIf { it.isNotBlank() },
                            respuesta.apellidoMaterno?.takeIf { it.isNotBlank() }
                        ).joinToString(" ")

                        vista.mostrarPerfil(
                            nombreCompleto = nombreCompleto,
                            correo = respuesta.correo ?: "",
                            telefono = respuesta.telefono ?: "Sin teléfono registrado"
                        )
                    } else {
                        vista.mostrarError(respuesta?.mensaje ?: "No se pudo cargar el perfil")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error del servidor: ${response.code()}"
                    vista.mostrarError(errorMsg)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al conectar con el servidor: ${e.localizedMessage}")
            }
        }
    }
}