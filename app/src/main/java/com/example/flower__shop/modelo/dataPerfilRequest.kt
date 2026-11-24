package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPerfilRequest(
    @SerialName("id_cliente") val idCliente: Int? = null,
    @SerialName("correo") val correo: String? = null
)