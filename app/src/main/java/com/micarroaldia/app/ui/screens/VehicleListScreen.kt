package com.micarroaldia.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micarroaldia.app.data.Vehicle
import com.micarroaldia.app.ui.components.PrimaryButton
import com.micarroaldia.app.ui.components.ScreenTopBar
import com.micarroaldia.app.ui.components.SecondaryButton
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray
import com.micarroaldia.app.ui.theme.SuccessGreen
import com.micarroaldia.app.ui.theme.SuccessGreenLight
import com.micarroaldia.app.ui.theme.SurfaceMuted
import com.micarroaldia.app.ui.theme.SurfaceWhite
import com.micarroaldia.app.ui.theme.TextSecondary
import com.micarroaldia.app.ui.theme.WarningAmber
import com.micarroaldia.app.ui.theme.WarningAmberLight
import com.micarroaldia.app.ui.theme.WarningBrown
import kotlinx.coroutines.launch

@Composable
fun VehicleListScreen(
    vehicles: List<Vehicle>,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onAddVehicleClick: () -> Unit,
    onGoHomeClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            ScreenTopBar(
                title = "Mis vehículos",
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding()
            ) {
                HorizontalDivider(color = OutlineGray)
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    PrimaryButton(text = "+ Agregar vehículo", onClick = onAddVehicleClick)
                    Spacer(modifier = Modifier.height(10.dp))
                    SecondaryButton(text = "Volver al inicio", onClick = onGoHomeClick)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "REGISTRADOS (${vehicles.size})",
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = TextSecondary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Selecciona para detalles",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                vehicles.forEach { vehicle ->
                    VehicleListItem(
                        vehicle = vehicle,
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Detalle del vehículo disponible próximamente")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun VehicleListItem(vehicle: Vehicle, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, OutlineGray)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(SurfaceMuted, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.DirectionsCar,
                    contentDescription = null,
                    tint = NavyPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = vehicle.plate,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    if (vehicle.pendingObligations == 0) {
                        StatusPill("Al día", SuccessGreen, SuccessGreenLight, SuccessGreen)
                    } else {
                        val count = vehicle.pendingObligations
                        StatusPill(
                            text = "$count obligaci${if (count > 1) "ones pendientes" else "ón pendiente"}",
                            dotColor = WarningAmber,
                            background = WarningAmberLight,
                            textColor = WarningBrown
                        )
                    }
                }
                Text(
                    text = "${vehicle.brand} ${vehicle.model}",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextSecondary
            )
        }
    }
}

@Composable
private fun StatusPill(text: String, dotColor: Color, background: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .background(background, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(6.dp).background(dotColor, CircleShape))
        Text(
            text = text,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
