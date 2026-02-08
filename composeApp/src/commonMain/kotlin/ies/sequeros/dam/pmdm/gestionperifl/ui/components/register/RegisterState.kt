package ies.sequeros.dam.pmdm.gestionperifl.ui.components.register

data class RegisterState (
    val username: String = "paco paco",
    val email: String = "paco@paco.es",
    val password: String = "1234567%8Pp",

    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isValid:Boolean = false,
    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null
)