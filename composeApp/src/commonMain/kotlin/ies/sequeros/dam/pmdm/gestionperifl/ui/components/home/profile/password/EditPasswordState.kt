package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.password

import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.UserStatus

data class EditPasswordState (
    val newPassword: String = "",
    val oldPassword: String = "",

    val newPasswordError: String? = null,
    val oldPasswordError: String? = null,

    val isLoading: Boolean = false,
    val isUpdateSuccess: Boolean = false,
    val isValid:Boolean = false,
    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null
)