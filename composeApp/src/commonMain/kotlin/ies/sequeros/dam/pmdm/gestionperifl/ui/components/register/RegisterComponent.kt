package ies.sequeros.dam.pmdm.gestionperifl.ui.components.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.register.RegisterFormViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterComponent (state: RegisterState,
                    onUsernameChange: (String) -> Unit,
                    onEmailChange: (String) -> Unit,
                    onPasswordChange: (String) -> Unit,
                    onRegisterClick: () -> Unit,
                    onCancel: () -> Unit) {

    val vm: RegisterFormViewModel = koinViewModel()
    val homeVm: HomeViewModel = koinViewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 400.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registrarse",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            //Campo de Username
            OutlinedTextField(
                value = state.username,
                onValueChange = { onUsernameChange(it) },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.usernameError != null,
                supportingText = {
                    state.usernameError?.let { Text(it) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Email
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.emailError != null,
                supportingText = {
                    state.emailError?.let { Text(it) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Contraseña
            OutlinedTextField(
                value = state.password,
                onValueChange = { onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = state.passwordError != null,
                supportingText = {
                    state.passwordError?.let { Text(it) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            // Error General
            if (state.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre botones
                ) {
                    // Botón de Cancelar (Secundario)
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    // Botón de Login (Primario)
                    Button(
                        onClick = {
                            onRegisterClick()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = state.isValid && !state.isLoading,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Registrarse")
                    }
                    if (state.errorMessage != null) {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}