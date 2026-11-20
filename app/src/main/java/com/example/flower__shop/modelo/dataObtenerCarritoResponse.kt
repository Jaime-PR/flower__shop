package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataObtenerCarritoResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String? = null,
    @SerialName("total_productos") val totalProductos: Int = 0,
    @SerialName("total_articulos") val totalArticulos: Int = 0,
    @SerialName("productos") val productos: List<dataProductoCarrito> = emptyList()
)
