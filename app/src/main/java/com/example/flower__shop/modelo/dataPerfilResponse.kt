package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPerfilResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("nombre") val nombre: String? = null,
    @SerialName("apellido_paterno") val apellidoPaterno: String? = null,
    @SerialName("apellido_materno") val apellidoMaterno: String? = null,
    @SerialName("correo") val correo: String? = null,
    @SerialName("telefono") val telefono: String? = null
)