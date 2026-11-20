package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataResumenCarritoResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String? = null,
    @SerialName("total_articulos") val totalArticulos: Int = 0,
    @SerialName("subtotal") val subtotal: Double = 0.0,
    @SerialName("envio") val envio: Double = 0.0,
    @SerialName("envio_gratis") val envioGratis: Boolean = false,
    @SerialName("envio_gratis_desde") val envioGratisDesde: Double = 500.0,
    @SerialName("total") val total: Double = 0.0
)
