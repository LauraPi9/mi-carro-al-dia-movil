package com.micarroaldia.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import com.micarroaldia.app.ui.components.AppHeader
import com.micarroaldia.app.ui.components.AppPasswordField
import com.micarroaldia.app.ui.components.AppTextField
import com.micarroaldia.app.ui.components.ErrorBanner
import com.micarroaldia.app.ui.components.PrimaryButton
import com.micarroaldia.app.ui.components.SecondaryButton
import com.micarroaldia.app.ui.theme.AccentBlue
import com.micarroaldia.app.ui.theme.OutlineGray
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

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
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()

            Spacer(modifier = Modifier.height(28.dp))

            if (showError) {
                ErrorBanner(
                    title = "Credenciales incorrectas",
                    description = "El correo electrónico o la contraseña ingresados no coinciden con nuestros registros."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            AppTextField(
                label = "Correo electrónico",
                value = email,
                onValueChange = {
                    email = it
                    showError = false
                },
                placeholder = "ejemplo@correo.com",
                isError = showError,
                errorText = "Verifica tu correo electrónico",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppPasswordField(
                label = "Contraseña",
                value = password,
                onValueChange = {
                    password = it
                    showError = false
                },
                placeholder = "••••••••",
                isError = showError,
                errorText = "Verifica tu contraseña"
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onForgotPasswordClick) {
                Text("¿Olvidaste tu contraseña?", color = AccentBlue)
            }

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton(
                text = "Iniciar sesión",
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        showError = true
                    } else {
                        showError = false
                        onLoginSuccess()
                    }
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            HorizontalDivider(color = OutlineGray)

            Spacer(modifier = Modifier.height(20.dp))

            SecondaryButton(
                text = "Registrar Usuario",
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Pantalla de registro disponible próximamente")
                    }
                }
            )
        }
    }
}
