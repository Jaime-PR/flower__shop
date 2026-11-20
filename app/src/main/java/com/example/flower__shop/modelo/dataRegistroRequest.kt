package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistroRequest(
    @SerialName("nombre_completo") val nombreCompleto: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String = "",
    @SerialName("password") val password: String = "",
    @SerialName("confirmar_password") val confirmarPassword: String = ""
)
