package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistroRequest(
    @SerialName("nombre") val nombre: String = "",
    @SerialName("apellido_paterno") val apellidoPaterno: String = "",
    @SerialName("apellido_materno") val apellidoMaterno: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String = "",
    @SerialName("password") val password: String = ""
)
