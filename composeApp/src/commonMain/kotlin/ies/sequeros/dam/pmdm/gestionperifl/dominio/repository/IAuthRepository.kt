package ies.sequeros.dam.pmdm.gestionperifl.dominio.repository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User

interface IAuthRepository {
    suspend fun register(command: RegisterUserCommand)

    suspend fun login(command: LoginUserCommand) : User

    suspend fun delete(command: RegisterUserCommand) : User
}