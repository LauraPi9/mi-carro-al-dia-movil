package com.micarroaldia.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.automirrored.outlined.NoteAdd
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micarroaldia.app.ui.theme.AccentBlue
import com.micarroaldia.app.ui.theme.AccentBlueLight
import com.micarroaldia.app.ui.theme.ErrorRed
import com.micarroaldia.app.ui.theme.ErrorRedLight
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray
import com.micarroaldia.app.ui.theme.PlateYellow
import com.micarroaldia.app.ui.theme.SuccessGreen
import com.micarroaldia.app.ui.theme.SurfaceMuted
import com.micarroaldia.app.ui.theme.SurfaceWhite
import com.micarroaldia.app.ui.theme.TextSecondary
import com.micarroaldia.app.ui.theme.WarningAmber
import com.micarroaldia.app.ui.theme.WarningAmberBorder
import com.micarroaldia.app.ui.theme.WarningAmberLight
import com.micarroaldia.app.ui.theme.WarningBrown
import kotlinx.coroutines.launch


private data class UpcomingObligation(val name: String, val dueText: String, val color: Color)

private val upcomingObligations = listOf(
    UpcomingObligation("SOAT (KLR-892)", "Vence en 5 días", ErrorRed),
    UpcomingObligation("Impuesto Vehicular", "Vence en 18 días", WarningAmber)
)

@Composable
fun DashboardScreen(
    onLogoutClick: () -> Unit,
    onRegisterVehicleClick: () -> Unit,
    onSeeAllVehiclesClick: () -> Unit,
    onNewObligationClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val showComingSoon: (String) -> Unit = { message ->
        coroutineScope.launch { snackbarHostState.showSnackbar(message) }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            DashboardHeader(userName = "Ana", onLogoutClick = onLogoutClick)

            HorizontalDivider(color = OutlineGray)

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                ObligationsAlertCard(
                    obligations = upcomingObligations,
                    onSeeDetailsClick = { showComingSoon("Detalle de obligaciones disponible próximamente") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                ActionCard(
                    title = "Registrar vehículo",
                    subtitle = "Añade placas, marca y modelo",
                    icon = Icons.Filled.Add,
                    dark = true,
                    onClick = onRegisterVehicleClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                ActionCard(
                    title = "+ Nueva obligación",
                    subtitle = "SOAT, Tecno, Impuestos, Seguro",
                    icon = Icons.AutoMirrored.Outlined.NoteAdd,
                    dark = false,
                    onClick = onNewObligationClick
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mis vehículos",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    CountBadge(count = 1, modifier = Modifier.padding(start = 8.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onSeeAllVehiclesClick) {
                        Text("Ver todos", color = AccentBlue, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }

                VehicleCard(
                    name = "Mazda CX-30",
                    plate = "KLR·892",
                    model = "Modelo 2022",
                    pendingCount = 1,
                    onClick = { showComingSoon("Detalle del vehículo disponible próximamente") }
                )
            }
        }
    }
}

@Composable
private fun DashboardHeader(userName: String, onLogoutClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF6366F1), AccentBlue)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(userName.take(1), color = SurfaceWhite, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 2.dp, y = 2.dp)
                    .size(12.dp)
                    .border(2.dp, SurfaceWhite, CircleShape)
                    .background(SuccessGreen, CircleShape)
            )
        }

        Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
            Text(
                text = "PANEL VEHICULAR",
                fontSize = 11.sp,
                letterSpacing = 1.sp,
                color = TextSecondary
            )
            Text(
                text = "Hola, $userName",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        OutlinedButton(
            onClick = onLogoutClick,
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, OutlineGray),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text("Salir", color = NavyPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null,
                tint = NavyPrimary,
                modifier = Modifier.padding(start = 6.dp).size(16.dp)
            )
        }
    }
}

@Composable
private fun ObligationsAlertCard(
    obligations: List<UpcomingObligation>,
    onSeeDetailsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(WarningAmberLight)
            .border(1.dp, WarningAmberBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(WarningAmberBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.NotificationsNone,
                    contentDescription = null,
                    tint = WarningAmber,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${obligations.size} obligaciones próximas",
                        color = WarningBrown,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Atención",
                        color = WarningBrown,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .background(WarningAmberBorder, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "Tienes vencimientos programados para este mes:",
                    color = WarningAmber,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                obligations.forEach { obligation ->
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .background(SurfaceWhite, RoundedCornerShape(8.dp))
                            .border(1.dp, WarningAmberBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(7.dp).background(obligation.color, CircleShape))
                        Text(
                            text = obligation.name,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp).weight(1f)
                        )
                        Text(
                            text = obligation.dueText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = obligation.color
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            color = WarningAmberBorder,
            modifier = Modifier.padding(top = 14.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .clip(RoundedCornerShape(6.dp))
                .clickable(onClick = onSeeDetailsClick)
                .padding(vertical = 6.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ver todos los detalles", color = WarningBrown, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = WarningBrown,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    dark: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (dark) NavyPrimary else SurfaceWhite
    val titleColor = if (dark) SurfaceWhite else NavyPrimary
    val subtitleColor = if (dark) SurfaceWhite.copy(alpha = 0.7f) else TextSecondary
    val iconBackground = if (dark) SurfaceWhite.copy(alpha = 0.12f) else AccentBlueLight
    val iconTint = if (dark) SurfaceWhite else AccentBlue

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = if (dark) null else BorderStroke(1.dp, AccentBlue.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(iconBackground, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Text(title, color = titleColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(subtitle, color = subtitleColor, fontSize = 12.sp)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = if (dark) SurfaceWhite else TextSecondary
            )
        }
    }
}

@Composable
private fun CountBadge(count: Int, modifier: Modifier = Modifier) {
    Text(
        text = count.toString(),
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextSecondary,
        modifier = modifier
            .background(SurfaceMuted, CircleShape)
            .padding(horizontal = 7.dp, vertical = 1.dp)
    )
}

@Composable
private fun VehicleCard(
    name: String,
    plate: String,
    model: String,
    pendingCount: Int,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, OutlineGray),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(SurfaceMuted, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsCar,
                        contentDescription = null,
                        tint = NavyPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NavyPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        if (pendingCount > 0) {
                            Row(
                                modifier = Modifier
                                    .background(ErrorRedLight, RoundedCornerShape(50))
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.size(6.dp).background(ErrorRed, CircleShape))
                                Text(
                                    text = "$pendingCount pendiente${if (pendingCount > 1) "s" else ""}",
                                    color = ErrorRed,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LicensePlate(plate)
                        Text(
                            text = model,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }

            HorizontalDivider(color = OutlineGray, modifier = Modifier.padding(vertical = 12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DocumentStatus(
                    label = "SOAT",
                    status = "Por renovar",
                    statusColor = ErrorRed,
                    modifier = Modifier.weight(1f)
                )
                DocumentStatus(
                    label = "TECNOMECÁNICA",
                    status = "Al día (Dic 2025)",
                    statusColor = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun LicensePlate(plate: String) {
    Text(
        text = plate,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = Color.Black,
        modifier = Modifier
            .background(PlateYellow, RoundedCornerShape(3.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(3.dp))
            .padding(horizontal = 6.dp, vertical = 1.dp)
    )
}

@Composable
private fun DocumentStatus(
    label: String,
    status: String,
    statusColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(SurfaceMuted, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text(label, fontSize = 10.sp, color = TextSecondary, letterSpacing = 0.5.sp)
        Text(status, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = statusColor)
    }
}
