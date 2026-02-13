package ies.sequeros.dam.pmdm.gestionperifl.application.usecase

import ies.sequeros.dam.pmdm.gestionperifl.application.command.UpdateUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IUserRepository

class UpdateUserUseCase(private val repository: IUserRepository) {
    suspend fun invoke(command: UpdateUserCommand): Result<Unit> {
        try {
            repository.patchUserInfo(command)
            return Result.success(Unit)
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}