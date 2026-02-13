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

    private val _dataToken = MutableStateFlow<String?>(null)
    val dataToken = _dataToken.asStateFlow()

    fun iniciarSesion(user: User, accessToken: String, refreshToken: String, dataToken: String) {
        _currentUser.update { user }
        _accessToken.update { accessToken }
        _refreshToken.update { refreshToken }
        _dataToken.update { dataToken }

        tokenStorage.saveTokens(accessToken, refreshToken, dataToken)
    }

    fun cerrarSesion() {
        _currentUser.update { null }
        _accessToken.update { null }
        _refreshToken.update { null }
        _dataToken.update { null }

        tokenStorage.clear()
    }

    fun recuperarSesion(): Boolean {
        val access = tokenStorage.getAccessToken()
        val refresh = tokenStorage.getRefreshToken()
        val data = tokenStorage.getDataToken()

        if (!data.isNullOrBlank()) {
            val tokenData = TokenJwt(data)
            val user = User(
                id = tokenData.payload.userId!!,
                username = tokenData.payload.userName!!,
                email = tokenData.payload.userEmail ?: "",
                image = tokenData.payload.userImage,
                status = tokenData.payload.status,
            )

            _currentUser.update { user }

            //_currentUser.value = user
            _accessToken.update { access }
            _refreshToken.update { refresh }
            return true
        }
        return false
    }

    fun haySesionActiva(): Boolean {
        return tokenStorage.getAccessToken() != null
    }

}