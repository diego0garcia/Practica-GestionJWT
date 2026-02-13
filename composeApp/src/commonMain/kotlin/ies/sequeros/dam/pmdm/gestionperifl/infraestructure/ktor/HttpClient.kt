package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.LoginRespuesta
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body

import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens

import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme.OAuth

import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(tokenStorage: TokenStorage,sesionManager: SesionManager,
                     refreshUrl:String): HttpClient {
    return HttpClient { // Puedes usar HttpClient(CIO), HttpClient(Darwin), etc.
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        //logs
        install(Logging) {

            //logger = Logger.DEFAULT
            logger = object : Logger {
                override fun log(message: String) {
                    println("KTOR CLIENT LOG: $message")
                }
            }
            level = LogLevel.ALL // O LogLevel.ALL para ver todo
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken = "mi_token_jwt", refreshToken =
                        "")
                }

                refreshTokens {
                    // Enviamos la petición de refresco como un mapa (JSON)
                    //end point de refresco
                    val response =
                        client.post("https://api.mi-app.com/auth/refresh") {
                            markAsRefreshTokenRequest()
                            //token de refresco
                            setBody(mapOf("refresh_token" to "token...."))
                        }

                    if (response.status == HttpStatusCode.OK) {
                        // Leemos la respuesta directamente como un Mapa
                        val data = response.body<Map<String, String>>()

                        // Extraer los valores usando las llaves del JSON
                        val newAccess = data["access_token"] ?: ""
                        val newRefresh = data["refresh_token"] ?: "antiguo refresh token" ?: ""
                        val idToken = data["id_token"] // Será null si es

                        // actualizra el almacen de tokens

                        // Opcional: Si existe id_token, aquí se podriña
                        if (idToken == null) {
                            println("Se ha recibido identidad (OIDC): $idToken")
                        }
                        BearerTokens(newAccess, newRefresh)
                    } else {
                        null
                    }
                }
            }
        }

                install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }
}