package com.example.flower__shop.presentador

import com.example.flower__shop.vista.pantalla_perfil
import android.content.Context

class clsPerfilPresentador(
    private val vista: pantalla_perfil
) {

    fun cargarPerfil(nombreExtra: String?, correoExtra: String?) {
        val prefs = vista.getSharedPreferences("flower_shop_prefs", Context.MODE_PRIVATE)

        var nombreFinal = ""

        if (nombreExtra != null && nombreExtra.trim().isNotEmpty()) {
            nombreFinal = nombreExtra
        } else {
            val nombreGuardado = prefs.getString("nombre", null)

            if (nombreGuardado != null && nombreGuardado.trim().isNotEmpty()) {
                nombreFinal = nombreGuardado
            } else {
                nombreFinal = "Usuario"
            }
        }

        var correoFinal = ""

        if (correoExtra != null && correoExtra.trim().isNotEmpty()) {
            correoFinal = correoExtra
        } else {
            val correoGuardado = prefs.getString("correo", null)

            if (correoGuardado != null && correoGuardado.trim().isNotEmpty()) {
                correoFinal = correoGuardado
            } else {
                correoFinal = "Sin correo registrado"
            }
        }

        vista.mostrarDatosPerfil(nombreFinal, correoFinal)
    }

    fun cerrarSesion() {
        val prefs = vista.getSharedPreferences("flower_shop_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        vista.navegarALogin()
    }
}