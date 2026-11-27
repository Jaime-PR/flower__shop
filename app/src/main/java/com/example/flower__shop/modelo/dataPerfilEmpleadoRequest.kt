package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPerfilEmpleadoRequest(
    @SerialName("id") val id: Int,
    @SerialName("correo") val correo: String
)