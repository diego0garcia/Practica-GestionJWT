package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.edit

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
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ComboBox
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.UserStatus
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.profile.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileComponent() {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
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