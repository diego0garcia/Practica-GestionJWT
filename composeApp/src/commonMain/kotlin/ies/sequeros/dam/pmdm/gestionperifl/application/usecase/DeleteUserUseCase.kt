package ies.sequeros.dam.pmdm.gestionperifl.application.usecase

import ies.sequeros.dam.pmdm.gestionperifl.application.command.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.dominio.repository.IUserRepository

class DeleteUserUseCase(private val repository: IUserRepository) {
    suspend fun invoke(command: DeleteUserCommand): Result<Unit> {
        try {
            repository.delete(command)
            return Result.success(Unit)
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}