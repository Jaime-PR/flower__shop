package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataTipoFlor(
    @SerialName("id_tipo_flor") val idTipoFlor: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("imagen_url") val imagenUrl: String? = null
)
