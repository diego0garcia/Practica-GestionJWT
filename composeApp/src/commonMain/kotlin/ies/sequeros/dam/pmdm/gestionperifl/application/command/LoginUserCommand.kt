package ies.sequeros.dam.pmdm.gestionperifl.application.command

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserCommand(
    val email: String,
    val password: String
)