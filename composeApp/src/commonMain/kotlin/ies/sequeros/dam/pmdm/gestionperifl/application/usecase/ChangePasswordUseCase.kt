package ies.sequeros.dam.pmdm.gestionperifl.application.usecase

import ies.sequeros.dam.pmdm.gestionperifl.application.command.ChangePasswordCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.command.UpdateUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IUserRepository

class ChangePasswordUseCase(private val repository: IUserRepository) {
    suspend fun invoke(command: ChangePasswordCommand): Result<Unit> {
        try {
            repository.putChangePassword(command)
            return Result.success(Unit)
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}