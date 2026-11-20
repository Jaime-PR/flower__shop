package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDetalleProductoResponse(
    @SerialName("exito") val exito: Boolean? = null,
    @SerialName("mensaje") val mensaje: String? = null,
    @SerialName("id_producto") val idProducto: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("descripcion") val descripcion: String? = null,
    @SerialName("detalles") val detalles: String? = null,
    @SerialName("imagen_url") val imagenUrl: String? = null,
    @SerialName("motivos") val motivos: List<String> = emptyList(),
    @SerialName("disponible") val disponible: Boolean = false,
    @SerialName("cantidad_maxima") val cantidadMaxima: Int = 0
)
