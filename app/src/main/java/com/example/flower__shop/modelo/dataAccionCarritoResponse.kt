package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataAccionCarritoResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("total_articulos") val totalArticulos: Int = 0,
    @SerialName("subtotal") val subtotal: Double = 0.0
)
