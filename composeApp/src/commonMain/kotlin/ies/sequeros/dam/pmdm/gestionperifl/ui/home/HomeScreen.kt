package ies.sequeros.dam.pmdm.gestionperifl.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.DeleteDialogComponet
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.HomeComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.LogOutDialogComponet
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.ProfileComponent

@Composable
fun HomeScreen(
    onCloseSesion: () -> Unit,
) {
    val vm = koinViewModel<HomeViewModel>()
    val appVm = koinViewModel<AppViewModel>()
    val profVm = koinViewModel<ProfileViewModel>()

    val updateUser by profVm.state.collectAsState()
    val deleteUser by profVm.deleteState.collectAsState()

    val navController = rememberNavController()
    val options by vm.options.collectAsState()

    var showDialogCloseSesion by remember { mutableStateOf(false) }
    var showDialogDeleteUser by remember { mutableStateOf(false) }

    LaunchedEffect(updateUser.isUpdateSuccess) {
        if (updateUser.isUpdateSuccess) {
            onCloseSesion()
        }
    }

    LaunchedEffect(deleteUser.isDeleted) {
        if (deleteUser.isDeleted) {
            onCloseSesion()
        }
    }

    vm.setOptions(
        listOf(
            ItemOption(
                Icons.Default.Home, {
                    navController.navigate(HomeRoutes.Main) {
                        launchSingleTop = true
                        popUpTo(HomeRoutes.Main)
                    }
                },
                "Home"
            ),
            ItemOption(
                Icons.Default.Person,
                {
                    navController.navigate(HomeRoutes.Profile) {
                        launchSingleTop = true
                        popUpTo(HomeRoutes.Profile)
                    }
                },
                ""
            ),
            ItemOption(
                Icons.Default.DarkMode,
                {
                    appVm.swithMode()
                },
                "Darkmode"
            ),
            ItemOption(
                Icons.Default.Close,
                {
                    showDialogCloseSesion = !showDialogCloseSesion
                }, "Close"
            )
        )
    )

    val navegador: @Composable () -> Unit = {
        NavHost(
            navController,
            startDestination = HomeRoutes.Main
        ) {
            composable(HomeRoutes.Main) {
                HomeComponent()
            }
            composable(HomeRoutes.Profile) {
                ProfileComponent(
                    {
                        showDialogDeleteUser = !showDialogDeleteUser
                    }
                )
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                options.forEach { item ->
                    // if(!item.admin || (item.admin && appViewModel.hasPermission()))
                    NavigationBarItem(
                        selected = true,
                        onClick = { item.action() },
                        icon = { Icon(item.icon, contentDescription = item.name) },
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            navegador()
        }
    }

    if (showDialogCloseSesion) {
        LogOutDialogComponet(
            onAccept = {
                showDialogCloseSesion = false
                onCloseSesion()
            },
            onDismiss = { showDialogCloseSesion = false }
        )
    }

    if (showDialogDeleteUser) {
        DeleteDialogComponet(
            onAccept = {
                showDialogDeleteUser = false
            },
            onDismiss = { showDialogDeleteUser = false }
        )
    }
}