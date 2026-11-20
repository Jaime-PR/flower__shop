package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataAgregarCarritoRequest(
    @SerialName("id_cliente") val idCliente: Int = 0,
    @SerialName("id_producto") val idProducto: Int = 0,
    @SerialName("cantidad") val cantidad: Int = 0
)
