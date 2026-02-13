package ies.sequeros.dam.pmdm.gestionperifl.di

import coil3.SingletonImageLoader.get
import ies.sequeros.dam.pmdm.gestionperifl.application.usecase.LoginUserUseCase
import ies.sequeros.dam.pmdm.gestionperifl.application.usecase.RegisterUserUseCase
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.createHttpClient
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppSettings
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.login.LoginFormViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.register.RegisterFormViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.EmptyCoroutineContext.get


val appModulo = module {

    single { TokenStorage(get()) }
    single { SesionManager(get()) }
    single { AppSettings() }
    /**
     * infraestructura
     */
    single {
        createHttpClient( get(),get(),
            "http://localhost:8080/api/public/refresh"
        )
    }
    //almacenamiento del token
    //repositorios
    /**
    capa de aplicación
    el sesion manager,
    el origen de los datos, se encarga de transforar el tokenstorage para trabajar con user
    casos de uso
     **/

    /**
    capa de presentación
     **/


    viewModel { AppViewModel(get(),get()) }
    viewModel { LoginFormViewModel(get(), get()) }
    viewModel { RegisterFormViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }

    factory { RegisterUserUseCase(get()) }
    factory { LoginUserUseCase(get()) }
}