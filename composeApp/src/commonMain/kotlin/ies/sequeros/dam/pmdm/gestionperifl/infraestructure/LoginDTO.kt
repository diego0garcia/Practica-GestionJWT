package ies.sequeros.dam.pmdm.gestionperifl.infraestructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginPeticion(
    val email: String,
    val password: String
)

@Serializable
data class LoginRespuesta(
    //Jose los serialname son para que coincidan con el JSon que se
    //devuelve en el Token
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("id_token")
    val idToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("expires_in")
    val expiresIn: Int,

    @SerialName("token_type")
    val tokenType: String
)