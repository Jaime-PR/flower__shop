package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistroResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("tipo_usuario") val tipoUsuario: String? = null,
    @SerialName("id_cliente") val idCliente: Int? = null,
    @SerialName("nombre") val nombre: String? = null,
    @SerialName("correo") val correo: String? = null,
    @SerialName("telefono") val telefono: String? = null
)
