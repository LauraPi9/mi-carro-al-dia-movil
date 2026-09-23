package com.micarroaldia.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.micarroaldia.app.data.Vehicle
import com.micarroaldia.app.ui.components.AppTextField
import com.micarroaldia.app.ui.components.PrimaryButton
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray
import com.micarroaldia.app.ui.theme.SuccessGreen
import com.micarroaldia.app.ui.theme.SuccessGreenLight
import com.micarroaldia.app.ui.theme.SurfaceMuted
import com.micarroaldia.app.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterVehicleScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onVehicleSaved: (Vehicle) -> Unit
) {
    var plate by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var showErrors by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showSuccessDialog) {
        VehicleCreatedDialog(
            onDismiss = {
                showSuccessDialog = false
                onVehicleSaved(
                    Vehicle(
                        plate = plate.replace(" ", ""),
                        brand = brand.trim(),
                        model = model.trim()
                    )
                )
            }
        )
    }

    Scaffold(
        modifier = if (showSuccessDialog) Modifier.blur(4.dp) else Modifier,
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Registrar vehículo", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        Surface(
                            onClick = onLogoutClick,
                            modifier = Modifier.padding(end = 12.dp).size(34.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, OutlineGray)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("salir", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
                HorizontalDivider(color = OutlineGray)
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                HorizontalDivider(color = OutlineGray)
                PrimaryButton(
                    text = "Guardar vehículo",
                    onClick = {
                        if (plate.isBlank() || brand.isBlank() || model.isBlank()) {
                            showErrors = true
                        } else {
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "DETALLES DEL AUTOMÓVIL",
                fontSize = 11.sp,
                letterSpacing = 1.sp,
                color = TextSecondary
            )
            Text(
                text = "Ingresa los datos del automotor para gestionar sus alertas y pagos.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            AppTextField(
                label = "Placa",
                value = plate,
                onValueChange = { plate = it.uppercase().take(7) },
                placeholder = "ABC 123",
                required = true,
                labelFontWeight = FontWeight.Bold,
                capitalization = KeyboardCapitalization.Characters,
                helperText = "Sin guiones ni espacios intermedios obligatorios",
                isError = showErrors && plate.isBlank(),
                errorText = "Ingresa la placa del vehículo",
                trailingContent = { CountryTag("COL") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                label = "Marca",
                value = brand,
                onValueChange = { brand = it },
                placeholder = "Toyota",
                required = true,
                labelFontWeight = FontWeight.Bold,
                capitalization = KeyboardCapitalization.Words,
                isError = showErrors && brand.isBlank(),
                errorText = "Ingresa la marca del vehículo"
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                label = "Modelo",
                value = model,
                onValueChange = { model = it },
                placeholder = "Corolla 2022",
                required = true,
                labelFontWeight = FontWeight.Bold,
                capitalization = KeyboardCapitalization.Words,
                isError = showErrors && model.isBlank(),
                errorText = "Ingresa el modelo del vehículo"
            )
        }
    }
}

@Composable
private fun VehicleCreatedDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Cerrar",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(SuccessGreenLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Vehículo creado",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "El vehículo se ha registrado exitosamente en el sistema.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PrimaryButton(text = "Entendido", onClick = onDismiss)
                }
            }
        }
    }
}

@Composable
private fun CountryTag(code: String) {
    Text(
        text = code,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        color = TextSecondary,
        modifier = Modifier
            .padding(end = 4.dp)
            .background(SurfaceMuted, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}
