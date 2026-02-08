package ies.sequeros.dam.pmdm.gestionperifl.application.command

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserCommand(
    val username: String,
    val email: String,
    val password: String
)