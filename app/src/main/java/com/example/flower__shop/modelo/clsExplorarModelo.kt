package com.example.flower__shop.modelo

import com.example.flower__shop.red.RetroFitCliente

class clsExplorarModelo {

    suspend fun obtenerTiposFlor(): List<dataTipoFlor> {
        return RetroFitCliente.apiService.obtenerTiposFlor()
    }

    suspend fun obtenerMotivos(): List<dataMotivo> {
        return RetroFitCliente.apiService.obtenerMotivos()
    }

    suspend fun obtenerProductos(
        buscar: String? = null,
        idCategoria: Int? = null,
        idMotivo: Int? = null,
        idTipoFlor: Int? = null,
        precioMin: Double? = null,
        precioMax: Double? = null
    ): List<dataProductoItem> {
        return RetroFitCliente.apiService.obtenerProductos(
            buscar = buscar,
            idCategoria = idCategoria,
            idMotivo = idMotivo,
            idTipoFlor = idTipoFlor,
            precioMin = precioMin,
            precioMax = precioMax
        )
    }
}