package ies.sequeros.dam.pmdm.gestionperifl.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.command.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecase.DeleteUserUseCase
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.DeleteState
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.ProfileState
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.register.RegisterState
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val _sesionManager: SesionManager,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _deleteState = MutableStateFlow(DeleteState())
    val deleteState: StateFlow<DeleteState> = _deleteState.asStateFlow()

    val isFormValid = MutableStateFlow(false)
    val isDeleteFormValid = MutableStateFlow(false)

    init {
        val user = _sesionManager.currentUser.value
        if (user != null) {
            _state.update {
                it.copy(
                    username = user.username,
                    email = user.email,
                )
            }
        }
    }

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = if (email.contains("@")) null else "Email no válido"
            )
        }
        validateForm()
    }

    fun onUsernameChange(username: String) {
        _state.update {
            it.copy(
                username = username,
                usernameError = if (username.length > 8) null else "Mínimo 8 carácteres"
            )
        }
        validateForm()
    }

    fun onDeletePasswordChange(password: String) {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()
        _deleteState.update {
            it.copy(
                password = password,
                passwordError = if (password.matches(passwordRegex)) null else "La contraseña debe incluir mayúscula, minúscula, número y un carácter especial"
            )
        }
        validateDeleteForm()
    }

    fun clear() {
        _state.update {
            ProfileState()
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            delete()
        }
    }

    private suspend fun delete() {
        _deleteState.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val deleteUserCommand =
                DeleteUserCommand(
                    password = deleteState.value.password
                )

            val result = deleteUserUseCase.invoke(deleteUserCommand)
                .onSuccess {
                    //_state.value = _state.value.copy(isLoginSuccess = true)
                    _deleteState.update { it.copy(isLoading = false, isLoginSuccess = true, isDeleted = true) }
                    _sesionManager.cerrarSesion()
                    //print("Respuesta: " + it)

                }.onFailure { error ->
                    _deleteState.update { it.copy(isLoading = false, isLoginSuccess = false) }
                    _deleteState.update {
                        it.copy(
                            isLoading = false,
                            isLoginSuccess = false,
                            errorMessage = error.message ?: "Ha ocurrido un error desconocido"
                        )
                    }
                }


        } catch (e: Exception) {
            _deleteState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Error al borrar: ${e.message}"
                )
            }
        } finally {
            _deleteState.value = _deleteState.value.copy(isLoading = false)
        }
    }

    private fun validateDeleteForm() {
        val s = _deleteState.value
        isDeleteFormValid.value = s.password.isNotBlank() && s.passwordError == null
        _deleteState.value = deleteState.value.copy(isValid = isDeleteFormValid.value)
    }

    private fun validateForm() {
        val s = _state.value
        isFormValid.value =
            s.username.isNotBlank() &&
                    s.email.isNotBlank() &&
                    s.usernameError == null &&
                    s.emailError == null
        _state.value = state.value.copy(isValid = isFormValid.value)
    }

}