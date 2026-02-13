package ies.sequeros.dam.pmdm.gestionperifl.application.command

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserCommand (
    val password: String
)