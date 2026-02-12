package ies.sequeros.dam.pmdm.gestionperifl.ui.components.login

import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User

data class LoginState(
    // Campos del formulario
    val email: String = "paco@paco.es",
    val password: String = "1234567%8Pp",

    // UI States
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isValid:Boolean = false,
    // Errores específicos de campo (validación local)
    val emailError: String? = null,
    val passwordError: String? = null,

    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null,

    //El usuario ya logeado lo recibimos aqui
    val user: User? = null,
    val token: String? = null
)