package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataProductoItem(
    @SerialName("id_producto") val idProducto: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("imagen_url") val imagenUrl: String? = null,
    @SerialName("disponible") val disponible: Boolean = false
)
