package com.micarroaldia.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.micarroaldia.app.ui.components.AppTextField
import com.micarroaldia.app.ui.components.PrimaryButton
import com.micarroaldia.app.ui.theme.AccentBlue
import com.micarroaldia.app.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onSendEmailClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "MiCarro al Día",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Recuperar contraseña",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Ingresa el correo electrónico registrado y te enviaremos las instrucciones para restablecer tu contraseña.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(28.dp))

                AppTextField(
                    label = "Correo electrónico registrado",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "usuario@dominio.com",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Enviar correo de recuperación",
                    onClick = {
                        if (email.isBlank()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Ingresa tu correo electrónico registrado")
                            }
                        } else {
                            onSendEmailClick()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "¿Sigues teniendo problemas?",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
                TextButton(onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Soporte disponible próximamente")
                    }
                }) {
                    Text("Contáctanos a Soporte", color = AccentBlue)
                }
            }
        }
    }
}
