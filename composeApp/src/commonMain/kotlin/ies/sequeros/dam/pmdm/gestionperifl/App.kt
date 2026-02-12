package ies.sequeros.dam.pmdm.gestionperifl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.dam.pmdm.gestionperifl.ui.AppRoutes
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.register.RegisterComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.init.InitComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.login.LoginScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.register.RegisterScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val appViewModel: AppViewModel = koinViewModel()

    val navController = rememberNavController()
    AppTheme(appViewModel.isDarkMode.collectAsState()) {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Init
        ){
            composable ( AppRoutes.Init ){
                InitComponent(
                    onLogin = {navController.navigate(AppRoutes.Login)},
                    onRegister = {navController.navigate(AppRoutes.Register)},
                    onEnter = {navController.navigate(AppRoutes.Home)}
                )
            }
            composable ( AppRoutes.Login ){
                LoginScreen(
                    onLogin = {navController.navigate(AppRoutes.Init)},
                    onCancel = {navController.navigate(AppRoutes.Init)}
                )
            }
            composable ( AppRoutes.Register ){
                RegisterScreen(
                    onRegister = {navController.navigate(AppRoutes.Home)},
                    onCancel = {navController.navigate(AppRoutes.Init)}
                )
            }
            composable ( AppRoutes.Home ){
                HomeScreen(
                    onCloseSesion = {navController.navigate(AppRoutes.Init)}
                )
            }
        }
    }
}