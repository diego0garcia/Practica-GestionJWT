package ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val settings: AppSettings,
    private val userSessionManager: SesionManager
) : ViewModel() {

    val isDarkMode = settings.isDarkMode

    fun toggleTheme() = settings.toggleDarkMode()

    fun setDarkMode() {
        settings.setDarkMode()
    }

    fun setLighMode() {
        settings.setLightMode()
    }

    fun swithMode() {
        settings.toggleDarkMode()
    }

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private fun checkSession() {
        viewModelScope.launch {
            _isLoggedIn.value = userSessionManager.haySesionActiva()
        }
    }
    val currentUser = userSessionManager.currentUser

    init {
        viewModelScope.launch {
            userSessionManager.recuperarSesion()
        }
    }
}
