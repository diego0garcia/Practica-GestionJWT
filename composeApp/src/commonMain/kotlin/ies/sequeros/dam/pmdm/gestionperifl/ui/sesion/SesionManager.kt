package ies.sequeros.dam.pmdm.gestionperifl.ui.sesion

import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SesionManager {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken = _refreshToken.asStateFlow()


    fun iniciarSesion(user: User, accessToken: String, refreshToken: String){
        _currentUser.update { currentUser -> user }
        _accessToken.update { token -> accessToken }
        _accessToken.update { token -> refreshToken }
    }

    fun cerrarSesion(){
        _currentUser.update { currentUser -> null }
        _accessToken.update { currentUser -> null }
        _refreshToken.update { currentUser -> null }
    }
}