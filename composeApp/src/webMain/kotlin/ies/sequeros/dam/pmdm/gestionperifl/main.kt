package ies.sequeros.dam.pmdm.gestionperifl

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import ies.sequeros.dam.pmdm.gestionperifl.di.appModulo
import ies.sequeros.dam.pmdm.gestionperifl.di.domainModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

val WasmPlatformModule = module {
    single<Settings> {
        StorageSettings()
    }
}
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(appModulo, domainModule, WasmPlatformModule)
    }
    ComposeViewport {
        App()
    }
}