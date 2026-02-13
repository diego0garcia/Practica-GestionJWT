package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ComboBox
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileComponent(
    onDeleteUser: () -> Unit
) {
    val vm: ProfileViewModel = koinViewModel()
    val state by vm.state.collectAsState()
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .defaultMinSize(minHeight = 200.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Información de Perfil",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            onDeleteUser()
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
                    IconButton(
                        onClick = {
                            //onDeleteUser()
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
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.username,
                onValueChange = { vm.onUsernameChange(it) },
                label = { Text("Usuario") },
                leadingIcon = { Icon(Icons.Default.PersonOutline, contentDescription = null) },
                isError = state.usernameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.usernameError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            ComboBox(
                UserStatus.entries,
                "Estado",
                state.status,
                { it.name.lowercase() },
                {
                    vm.onStatusChange(it)
                },
                true
            )

            state.statusError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalButton(onClick = { vm.clear() }) {
                        Icon(Icons.Default.Autorenew, contentDescription = null)
                    }
                    Button(
                        onClick = {
                            vm.onUpdate()
                        },
                        enabled = state.isValid
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                    }
                }
            }
        }
    }
}