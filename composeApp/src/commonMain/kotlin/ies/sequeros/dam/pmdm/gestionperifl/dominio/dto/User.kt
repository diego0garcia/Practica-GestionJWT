package ies.sequeros.dam.pmdm.gestionperifl.dominio.dto

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: String,
    val username: String,
    val email: String,
    val image: String?,
    val status: String?,
)