package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsRegistroModelo
import com.example.flower__shop.vista.crear_cuenta_cliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class clsRegistroPresentador(
    private var vista: crear_cuenta_cliente?,
    private val modelo: clsRegistroModelo = clsRegistroModelo()
) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun validarYRegistrar(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        correo: String,
        telefono: String,
        password: String,
        confirmarPassword: String
    ) {
        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() ||
            correo.isEmpty() || telefono.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()
        ) {
            vista?.mostrarError("Por favor, completa todos los campos.")
            return
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            vista?.mostrarError("Ingresa un correo electrónico válido.")
            return
        }

        if (telefono.length != 10) {
            vista?.mostrarError("El teléfono debe tener 10 dígitos.")
            return
        }

        val tieneMayuscula = password.any { it.isUpperCase() }
        val tieneMinuscula = password.any { it.isLowerCase() }
        val tieneEspecial = password.any { !it.isLetterOrDigit() && !it.isWhitespace() }

        if (password.length < 8 || !tieneMayuscula || !tieneMinuscula || !tieneEspecial) {
            vista?.mostrarError("La contraseña debe tener mínimo 8 caracteres, mayúsculas,minúsculas y un carácter especial.")
            return
        }

        if (password != confirmarPassword) {
            vista?.mostrarError("Las contraseñas no coinciden.")
            return
        }

        vista?.mostrarCargando(true)

        scope.launch {
            try {
                val response = modelo.registrarCliente(
                    nombre = nombre,
                    apellidoPaterno = apellidoPaterno,
                    apellidoMaterno = apellidoMaterno,
                    correo = correo,
                    telefono = telefono,
                    password = password
                )

                if (response.isSuccessful && response.body() != null) {
                    val datos = response.body()!!
                    if (datos.exito) {
                        vista?.registroExitoso(datos)
                    } else {
                        vista?.mostrarError(
                            datos.mensaje.ifEmpty { "No se pudo crear la cuenta." }
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