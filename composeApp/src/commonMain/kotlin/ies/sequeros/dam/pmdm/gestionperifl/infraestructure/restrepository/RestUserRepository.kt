package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.restrepository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IAuthRepository
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.LoginRespuesta
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class RestUserRepository(private val url: String, private val _client: HttpClient, private val sesionManager: SesionManager) : IUserRepository {
    override suspend fun getAllInfo(command: RegisterUserCommand) {
        TODO("Not yet implemented")
    }

    override suspend fun putChangePassword(command: LoginUserCommand) {
        TODO("Not yet implemented")
    }

    override suspend fun patchChangePassword(command: LoginUserCommand) {
        TODO("Not yet implemented")
    }

    override suspend fun patchChangeImage(command: LoginUserCommand) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(command: DeleteUserCommand) {
        val token = sesionManager.accessToken.value
        val request = _client.delete(url) {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(command)
        }
    }
}