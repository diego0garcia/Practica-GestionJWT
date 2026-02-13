package ies.sequeros.dam.pmdm.gestionperifl.ui.sesion

import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SesionManager(private val tokenStorage: TokenStorage) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken = _refreshToken.asStateFlow()

    fun iniciarSesion(user: User, accessToken: String, refreshToken: String){
        _currentUser.update { user }
        _accessToken.update { accessToken }
        _refreshToken.update { refreshToken }

        tokenStorage.saveTokens(accessToken, refreshToken)
    }

    fun cerrarSesion(){
        _currentUser.update { null }
        _accessToken.update { null }
        _refreshToken.update { null }

        tokenStorage.clear()
    }

    fun recuperarSesion(): Boolean {
        val access = tokenStorage.getAccessToken()
        val refresh = tokenStorage.getRefreshToken()

        if (!access.isNullOrBlank()) {
            try {
                val tokenData = TokenJwt(access)
                val user = User(
                    id = tokenData.payload.userId ?: "",
                    username = tokenData.payload.userName ?: "",
                    email = tokenData.payload.userEmail ?: "",
                    image = tokenData.payload.userImage
                )

                _currentUser.value = user
                _accessToken.value = access
                _refreshToken.value = refresh
                return true
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }
    fun haySesionActiva(): Boolean {
        return tokenStorage.getAccessToken() != null
    }


}