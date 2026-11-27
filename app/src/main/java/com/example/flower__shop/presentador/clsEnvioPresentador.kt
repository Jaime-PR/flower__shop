package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsEnvioModelo
import com.example.flower__shop.modelo.dataEnvioRequest
import com.example.flower__shop.vista.pantalla_envio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class clsEnvioPresentador(
    private var vista: pantalla_envio?,
    private val modelo: clsEnvioModelo = clsEnvioModelo()
) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun cargarResumenPedido(idCliente: Int) {
        if (idCliente <= 0) return

        scope.launch {
            try {
                val respCarrito = modelo.obtenerCarrito(idCliente)
                val respResumen = modelo.obtenerResumenCarrito(idCliente)

                if (respCarrito.exito && respResumen.exito) {
                    vista?.mostrarResumen(
                        productos = respCarrito.productos,
                        resumen = respResumen
                    )
                }
            } catch (e: Exception) {
                vista?.mostrarError("Error al cargar datos del carrito: ${e.localizedMessage}")
            }
        }
    }

    fun validarYRegistrarEnvio(
        idCliente: Int,
        nombre: String,
        telefono: String,
        direccion: String,
        colonia: String,
        ciudad: String,
        codigoPostal: String,
        fechaEntrega: String,
        franjaHoraria: String,
        notaPersonalizada: String
    ) {
        if (idCliente <= 0) {
            vista?.mostrarError("Debes iniciar sesión para registrar los datos de envío.")
            return
        }

        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
            colonia.isEmpty() || ciudad.isEmpty() || codigoPostal.isEmpty() ||
            fechaEntrega.isEmpty() || franjaHoraria.isEmpty()
        ) {
            vista?.mostrarError("Por favor, completa todos los campos obligatorios.")
            return
        }

        if (telefono.length != 10) {
            vista?.mostrarError("El teléfono debe tener 10 dígitos.")
            return
        }

        if (codigoPostal.length != 5) {
            vista?.mostrarError("El código postal debe tener 5 dígitos.")
            return
        }

        // Validación de fecha y hora que no sean anteriores a la actual
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        try {
            val fechaHoraSeleccionada = sdf.parse("$fechaEntrega $franjaHoraria")
            val fechaHoraActual = Calendar.getInstance().time

            if (fechaHoraSeleccionada != null && fechaHoraSeleccionada.before(fechaHoraActual)) {
                vista?.mostrarError("La fecha y hora de entrega no pueden ser anteriores a la fecha y hora actual.")
                return
            }
        } catch (e: Exception) {
            // Si el formato de hora es una franja de texto o no coincide exactamente, validamos sólo fecha
            val sdfFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            try {
                val fechaSeleccionada = sdfFecha.parse(fechaEntrega)
                val calendarHoy = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time

                if (fechaSeleccionada != null && fechaSeleccionada.before(calendarHoy)) {
                    vista?.mostrarError("La fecha de entrega no puede ser anterior a la fecha actual.")
                    return
                }
            } catch (ex: Exception) {
                vista?.mostrarError("Formato de fecha u hora no válido.")
                return
            }
        }

        vista?.mostrarCargando(true)

        scope.launch {
            try {
                val request = dataEnvioRequest(
                    idCliente = idCliente,
                    nombreDestinatario = nombre,
                    telefonoDestinatario = telefono,
                    direccion = direccion,
                    colonia = colonia,
                    ciudad = ciudad,
                    codigoPostal = codigoPostal,
                    fechaEntrega = fechaEntrega,
                    franjaHoraria = franjaHoraria,
                    notaPersonalizada = notaPersonalizada,
                    esGuardada = 1
                )

                val response = modelo.registrarEnvio(request)

                if (response.isSuccessful && response.body() != null) {
                    val datos = response.body()!!
                    if (datos.exito) {
                        try {
                            modelo.vaciarCarrito(idCliente)
                        } catch (_: Exception) {
                        }
                        vista?.envioRegistradoExitosamente(
                            mensaje = datos.mensaje.ifEmpty { "Envío registrado con éxito" },
                            idEnvio = datos.idEnvio
                        )
                    } else {
                        vista?.mostrarError(datos.mensaje.ifEmpty { "Error al registrar el envío." })
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