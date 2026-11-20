package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataVaciarCarritoRequest(
    @SerialName("id_cliente") val idCliente: Int = 0
)
