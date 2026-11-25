package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsPerfilModelo
import com.example.flower__shop.vista.pantalla_perfil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPerfilPresentador(
    private val vista: pantalla_perfil,
    private val idCliente: Int = 1
) {

    private val modelo = clsPerfilModelo()

    fun iniciar() {
        cargarPerfil()
    }

    fun cargarPerfil() {
        if (idCliente <= 0) {
            vista.mostrarError("No se encontró un cliente activo")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = modelo.perfil_cliente(idCliente)
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
                    vista.mostrarError("Error del servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al conectar con el servidor: ${e.localizedMessage}")
            }
        }
    }
}