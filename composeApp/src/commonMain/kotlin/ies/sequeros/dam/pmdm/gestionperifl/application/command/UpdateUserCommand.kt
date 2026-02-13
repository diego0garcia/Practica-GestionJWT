package ies.sequeros.dam.pmdm.gestionperifl.application.command

import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.UserStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserCommand (
    val name: String,
    val status: UserStatus
)