package com.example.flower__shop.presentador

import com.example.flower__shop.modelo.clsInicioModelo
import com.example.flower__shop.vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsInicioPresentador(private val vista: MainActivity) {

    private val modeloInicio = clsInicioModelo()

    fun iniciar() {
        cargarOcasiones()
        cargarProductosDestacados()
    }

    fun cargarOcasiones() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val ocasiones = modeloInicio.obtenerOcasiones()
                vista.mostrarOcasiones(ocasiones)
            } catch (e: Exception) {
                vista.mostrarError(
                    "Error al cargar ocasiones: " + e.localizedMessage
                )
            }
        }
    }

    fun cargarProductosDestacados() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val productos = modeloInicio.obtenerProductosDestacados()
                vista.mostrarProductosDestacados(productos)
            } catch (e: Exception) {
                vista.mostrarError(
                    "Error al cargar productos destacados: " + e.localizedMessage
                )
            }
        }
    }
}