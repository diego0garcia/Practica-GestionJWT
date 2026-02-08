package ies.sequeros.dam.pmdm.gestionperifl.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.LaunchedEffect
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.login.LoginComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.register.RegisterComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.login.LoginFormViewModel

@Composable
fun RegisterScreen(
    onLogin: () -> Unit,
    onCancel: () -> Unit,
) {
    val viewModel = koinViewModel<RegisterFormViewModel>()
    //estado del formulario que es el del LoginComponent
    val state by viewModel.state.collectAsState()
    //cuando el estado pasa a ser correcto, se avisa al padre
    LaunchedEffect(state.isLoginSuccess) {
        if (state.isLoginSuccess) {
            onLogin()
        }
    }

    RegisterComponent(
        state, viewModel::onUsernameChange, viewModel::onEmailChange, viewModel::onPasswordChange,
        {
            viewModel.register()
        },
        {
            onCancel()
        })

}