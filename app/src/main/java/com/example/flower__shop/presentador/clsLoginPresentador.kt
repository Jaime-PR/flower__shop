package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsLoginModelo
import com.example.flower__shop.vista.iniciar_sesion_cliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class clsLoginPresentador(
    private var vista: iniciar_sesion_cliente?,
    private val modelo: clsLoginModelo = clsLoginModelo()
) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun validarEIniciarSesion(correo: String, password: String) {
        if (correo.isEmpty() || password.isEmpty()) {
            vista?.mostrarError("Por favor, ingresa tu correo y contraseña.")
            return
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            vista?.mostrarError("Ingresa un correo electrónico válido.")
            return
        }

        vista?.mostrarCargando(true)

        scope.launch {
            try {
                val response = modelo.iniciarSesion(correo, password)

                if (response.isSuccessful && response.body() != null) {
                    val datos = response.body()!!
                    if (datos.exito) {
                        vista?.loginExitoso(datos)
                    } else {
                        vista?.mostrarError(
                            datos.mensaje.ifEmpty { "Correo o contraseña incorrectos." }
                        )
                    }
                } else {
                    vista?.mostrarError("Error en el servidor (${response.code()}).")
                }
            } catch (e: Exception) {
                vista?.mostrarError("Error de conexión: verifica tu internet.")
            } finally {
                vista?.mostrarCargando(false)
            }
        }
    }

    fun onDestroy() {
        job.cancel()
        vista = null
    }
}