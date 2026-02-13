package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home

data class DeleteState (
    val password: String = "",

    val passwordError: String? = null,

    val isValid:Boolean = false,

    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isDeleted: Boolean = false,
    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null
)