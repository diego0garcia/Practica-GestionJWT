package ies.sequeros.dam.pmdm.gestionperifl.dominio.repository

import ies.sequeros.dam.pmdm.gestionperifl.application.command.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.LoginUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.RegisterUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.dto.User

interface IUserRepository {
    suspend fun getAllInfo(command: RegisterUserCommand)

    suspend fun putChangePassword(command: LoginUserCommand)

    suspend fun patchChangePassword(command: LoginUserCommand)

    suspend fun patchChangeImage(command: LoginUserCommand)

    suspend fun delete(command: DeleteUserCommand)
}