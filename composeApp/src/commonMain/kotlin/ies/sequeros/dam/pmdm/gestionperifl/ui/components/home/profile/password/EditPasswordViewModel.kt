package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.command.ChangePasswordCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecase.ChangePasswordUseCase
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditPasswordViewModel(
    private val _sesionManager: SesionManager,
    val changePassword: ChangePasswordUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditPasswordState())
    val state = _state.asStateFlow()

    fun cerrarSesion(){
        _sesionManager.cerrarSesion()
    }

    val isFormValid = MutableStateFlow(false)

    fun onOldPasswordChange(oldPassword: String) {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()
        _state.update {
            it.copy(
                oldPassword = oldPassword,
                oldPasswordError = if (oldPassword.matches(passwordRegex)) null else "La contraseña debe incluir mayúscula, minúscula, número y un carácter especial"
            )
        }
        validateForm()
    }

    fun onNewPasswordChange(newPassword: String) {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()
        _state.update {
            it.copy(
                newPassword = newPassword,
                newPasswordError = if (newPassword.matches(passwordRegex)) null else "La contraseña debe incluir mayúscula, minúscula, número y un carácter especial"
            )
        }
        validateForm()
    }

    fun clear() {
        _state.update {
            EditPasswordState()
        }
    }

    private fun validateForm() {
        val s = _state.value
        isFormValid.value =
            s.oldPassword.isNotBlank() &&
                    s.newPassword.isNotBlank() &&
                    s.oldPasswordError == null &&
                    s.newPasswordError == null
        _state.value = state.value.copy(isValid = isFormValid.value)
    }

    fun onSend(){
        viewModelScope.launch {
            changePassword()
        }
    }

    private suspend fun changePassword() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val updatePasswordCommand =
                ChangePasswordCommand(
                    newPassword = _state.value.newPassword,
                    oldPassword = _state.value.oldPassword
                )

            val result = changePassword.invoke(updatePasswordCommand)
                .onSuccess {
                    //_state.value = _state.value.copy(isLoginSuccess = true)
                    _state.update { it.copy(isLoading = false, isUpdateSuccess = true) }
                    _sesionManager.cerrarSesion()
                    //print("Respuesta: " + it)

                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false, isUpdateSuccess = false) }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isUpdateSuccess = false,
                            errorMessage = error.message ?: "Ha ocurrido un error desconocido"
                        )
                    }
                }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Error al borrar: ${e.message}"
                )
            }
        } finally {
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}