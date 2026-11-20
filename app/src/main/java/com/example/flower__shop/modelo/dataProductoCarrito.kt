package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataProductoCarrito(
    @SerialName("id_producto") val idProducto: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("imagen_url") val imagenUrl: String? = null,
    @SerialName("cantidad") val cantidad: Int = 0,
    @SerialName("importe") val importe: Double = 0.0
)
