package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home

data class ProfileState (
    val username: String = "",
    val email: String = "",
    val password: String = "",

    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isValid:Boolean = false,
    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null
)