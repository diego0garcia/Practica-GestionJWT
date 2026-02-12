package ies.sequeros.dam.pmdm.gestionperifl.ui.sesion

import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SesionManager {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()
    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()


    fun iniciarSesion(user: User, token: String){
        _currentUser.update { user -> user }
        _token.update { token -> token }
    }

    fun cerrarSesion(){
        _currentUser.update { user -> null }
        _token.update { user -> null }
    }
}