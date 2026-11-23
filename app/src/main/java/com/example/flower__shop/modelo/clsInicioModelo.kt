package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente

class clsInicioModelo {

    suspend fun obtenerOcasiones(): List<dataMotivo> {
        return RetroFitCliente.apiService.obtenerMotivos()
    }

    suspend fun obtenerProductosDestacados(): List<dataProductoItem> {
        return RetroFitCliente.apiService.obtenerProductosDestacados()
    }
}