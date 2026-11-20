package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataEliminarCarritoRequest(
    @SerialName("id_cliente") val idCliente: Int = 0,
    @SerialName("id_producto") val idProducto: Int = 0
)
