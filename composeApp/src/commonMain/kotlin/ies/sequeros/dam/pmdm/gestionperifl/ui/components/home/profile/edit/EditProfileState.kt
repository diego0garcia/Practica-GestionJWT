package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.edit

import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.UserStatus

data class EditProfileState (
    val username: String = "",
    val status: UserStatus = UserStatus.PENDING,
    val password: String = "",

    val usernameError: String? = null,
    val statusError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val isUpdateSuccess: Boolean = false,
    val isValid:Boolean = false,
    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null
)