package ies.sequeros.dam.pmdm.gestionperifl.ui.components.home.profile.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.dam.pmdm.gestionperifl.ui.sesion.SesionManager
import org.koin.compose.koinInject

@Composable
fun InfoProfileComponent(sesionManager: SesionManager = koinInject()) {
    
    val usuario by sesionManager.currentUser.collectAsState()
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
            Text(
                text = "Detalles del Usuario",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            usuario?.let { user ->
                Text("La id de este usuario es: ${user.id.toString()}", style = MaterialTheme.typography.bodyMedium)
                Text("El username de este usuario es: ${user.username}", style = MaterialTheme.typography.bodyMedium)
                Text("El email de este usuario es: ${user.email}", style = MaterialTheme.typography.bodyMedium)
                Text("El status de este usuario es: ${user.status.toString()}")
                }
            }
        }
    }
