package ies.sequeros.dam.pmdm.gestionperifl.ui.home.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.delete.DeleteDialogComponet
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.edit.ProfileComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.info.InfoProfileComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.password.EditPasswordProfileComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.ProfileViewModel

@Composable
fun ProfileScreen(
    onCloseSesion: () -> Unit,
) {
    val vm = koinViewModel<HomeViewModel>()
    val profVm = koinViewModel<ProfileViewModel>()

    val navController = rememberNavController()

    val updateUser by profVm.state.collectAsState()
    val deleteUser by profVm.deleteState.collectAsState()

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
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .defaultMinSize(minHeight = 30.dp),
            tonalElevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                //modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Gestión del Perfil",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(ProfileRoutes.Info)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(ProfileRoutes.Edit)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(ProfileRoutes.Password)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Password,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            showDialogDeleteUser = !showDialogDeleteUser
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    )
                }
            }
        }
        NavHost(
            navController = navController,
            startDestination = ProfileRoutes.Info
        ) {
            composable(ProfileRoutes.Info) {
                InfoProfileComponent()
            }
            composable(ProfileRoutes.Edit) {
                ProfileComponent()
            }
            composable(ProfileRoutes.Password) {
                EditPasswordProfileComponent()
            }
        }
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