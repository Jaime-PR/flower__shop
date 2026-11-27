package com.example.flower__shop.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataEnvioRequest(
    @SerialName("id_cliente") val idCliente: Int = 0,
    @SerialName("nombre_destinatario") val nombreDestinatario: String = "",
    @SerialName("telefono_destinatario") val telefonoDestinatario: String = "",
    @SerialName("direccion") val direccion: String = "",
    @SerialName("colonia") val colonia: String = "",
    @SerialName("ciudad") val ciudad: String = "Pachuca",
    @SerialName("codigo_postal") val codigoPostal: String = "",
    @SerialName("fecha_entrega") val fechaEntrega: String = "",
    @SerialName("franja_horaria") val franjaHoraria: String = "",
    @SerialName("nota_personalizada") val notaPersonalizada: String = "",
    @SerialName("es_guardada") val esGuardada: Int = 1
)