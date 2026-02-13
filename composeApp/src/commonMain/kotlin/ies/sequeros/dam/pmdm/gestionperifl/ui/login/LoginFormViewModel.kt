package ies.sequeros.dam.pmdm.gestionperifl.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecase.LoginUserUseCase
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository

import ies.sequeros.dam.pmdm.gestionperifl.ui.components.login.LoginState
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class LoginFormViewModel(
    repository: IAuthRepository,
    val loginUseCase: LoginUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = if (email.contains("@")) null else "Email no válido"
            )
        }
        validateForm()
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
                passwordError = if (password.length >= 6) null else "Mínimo 6 caracteres"
            )
        }
        validateForm()
    }

    private fun validateForm() {
        val s = _state.value
        isFormValid.value = s.email.isNotBlank() &&
                s.password.isNotBlank() &&
                s.emailError == null &&
                s.passwordError == null
        _state.value = state.value.copy(isValid = isFormValid.value)
    }

    fun onLogin() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val loginCommand = LoginUserCommand(
                email = state.value.email,
                password = state.value.password
            )

            loginUseCase.invoke(loginCommand)
                .onSuccess { result ->
                    _state.update {
                        it.copy(isLoading = false, isLoginSuccess = true, user = result)
                    }
                }
                .onFailure {
                    _state.update { it.copy(isLoading = false, isLoginSuccess = false, errorMessage = "Email o contraseña incorrecto") }

                }
        }
    }

    suspend fun login() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            _state.value = state.value.copy(isLoading = true)

            val loginComand = LoginUserCommand(
                email = state.value.email,
                password = state.value.password
            )

            val result = loginUseCase.invoke(loginComand)
                .onSuccess { result ->
                    //_state.value = _state.value.copy(isLoginSuccess = true)
                    _state.update { it.copy(isLoading = false, isLoginSuccess = true, user = result) }
                    //print("Respuesta: " + it)

                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false, isLoginSuccess = false) }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoginSuccess = false,
                            errorMessage = error.message ?: "Ha ocurrido un error desconocido"
                        )
                    }
                }

        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Error al conectar: ${e.message}"
                )
            }
        } finally {
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}