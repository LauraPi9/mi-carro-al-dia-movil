package com.micarroaldia.app.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NoteAdd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.micarroaldia.app.data.SimpleDate
import com.micarroaldia.app.data.Vehicle
import com.micarroaldia.app.ui.components.AppPickerField
import com.micarroaldia.app.ui.components.AppTextField
import com.micarroaldia.app.ui.components.MonthCalendar
import com.micarroaldia.app.ui.components.PrimaryButton
import com.micarroaldia.app.ui.components.ScreenTopBar
import com.micarroaldia.app.ui.components.SuccessDialog
import com.micarroaldia.app.ui.theme.AccentBlue
import com.micarroaldia.app.ui.theme.AccentBlueLight
import com.micarroaldia.app.ui.theme.ErrorRed
import com.micarroaldia.app.ui.theme.ErrorRedLight
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray
import com.micarroaldia.app.ui.theme.SuccessGreen
import com.micarroaldia.app.ui.theme.SuccessGreenLight
import com.micarroaldia.app.ui.theme.SurfaceMuted
import com.micarroaldia.app.ui.theme.SurfaceWhite
import com.micarroaldia.app.ui.theme.TextSecondary
import com.micarroaldia.app.ui.theme.WarningAmberLight
import com.micarroaldia.app.ui.theme.WarningBrown

private const val TOTAL_STEPS = 3

@Composable
fun NewObligationScreen(
    vehicles: List<Vehicle>,
    onExit: () -> Unit,
    onLogoutClick: () -> Unit,
    onAlarmCreated: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }

    var name by remember { mutableStateOf("") }
    var vehicle by remember { mutableStateOf<Vehicle?>(null) }
    var dueDate by remember { mutableStateOf(SimpleDate.today()) }
    var alarmDate by remember { mutableStateOf<SimpleDate?>(null) }
    var alarmHour by remember { mutableIntStateOf(8) }
    var alarmMinute by remember { mutableIntStateOf(0) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val goBack: () -> Unit = { if (step > 1) step-- else onExit() }
    BackHandler(enabled = step > 1) { step-- }

    if (showSuccessDialog) {
        SuccessDialog(
            title = "Alarma creada",
            message = "Tu recordatorio para $name ha sido programado con éxito.",
            icon = Icons.Filled.Check,
            iconTint = SurfaceWhite,
            iconBackground = NavyPrimary,
            onDismiss = {
                showSuccessDialog = false
                onAlarmCreated()
            }
        )
    }

    Scaffold(
        modifier = if (showSuccessDialog) Modifier.blur(4.dp) else Modifier,
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                ScreenTopBar(
                    title = when (step) {
                        1 -> "Nueva Obligación"
                        2 -> "Fecha de vencimiento"
                        else -> "Crear alarma"
                    },
                    onBackClick = goBack,
                    onLogoutClick = onLogoutClick,
                    showDivider = false
                )
                StepProgress(currentStep = step)
                HorizontalDivider(color = OutlineGray)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (step) {
                1 -> ObligationDetailsStep(
                    name = name,
                    onNameChange = { name = it },
                    vehicles = vehicles,
                    selectedVehicle = vehicle,
                    onVehicleSelected = { vehicle = it },
                    onNextClick = { step = 2 }
                )
                2 -> DueDateStep(
                    dueDate = dueDate,
                    onDueDateChange = { dueDate = it },
                    onSaveClick = {
                        alarmDate = maxOf(dueDate.plusDays(-14), SimpleDate.today())
                        step = 3
                    }
                )
                else -> AlarmStep(
                    name = name,
                    vehicle = vehicle,
                    dueDate = dueDate,
                    alarmDate = alarmDate ?: SimpleDate.today(),
                    onAlarmDateChange = { alarmDate = it },
                    hour = alarmHour,
                    minute = alarmMinute,
                    onTimeChange = { h, m ->
                        alarmHour = h
                        alarmMinute = m
                    },
                    onCreateClick = { showSuccessDialog = true }
                )
            }
        }
    }
}

@Composable
private fun StepProgress(currentStep: Int) {
    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (i in 1..TOTAL_STEPS) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            if (i <= currentStep) NavyPrimary else OutlineGray,
                            RoundedCornerShape(50)
                        )
                )
            }
        }
        Text(
            text = "paso $currentStep de $TOTAL_STEPS",
            fontSize = 11.sp,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.End).padding(top = 6.dp)
        )
    }
}



@Composable
private fun ObligationDetailsStep(
    name: String,
    onNameChange: (String) -> Unit,
    vehicles: List<Vehicle>,
    selectedVehicle: Vehicle?,
    onVehicleSelected: (Vehicle) -> Unit,
    onNextClick: () -> Unit
) {
    var showErrors by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        AppTextField(
            label = "Nombre de la obligación",
            value = name,
            onValueChange = onNameChange,
            placeholder = "ej. SOAT",
            required = true,
            labelFontWeight = FontWeight.Bold,
            capitalization = KeyboardCapitalization.Sentences,
            helperText = "ej. SOAT, tecnomecánica, seguro todo riesgo, impuesto vehicular",
            isError = showErrors && name.isBlank(),
            errorText = "Ingresa el nombre de la obligación"
        )

        Spacer(modifier = Modifier.height(20.dp))

        VehicleDropdown(
            vehicles = vehicles,
            selected = selectedVehicle,
            onSelected = onVehicleSelected,
            isError = showErrors && selectedVehicle == null
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceMuted, RoundedCornerShape(10.dp))
                .padding(14.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "En los siguientes pasos configurarás la fecha límite de pago o vencimiento y la alerta previa.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "Siguiente",
            onClick = {
                if (name.isBlank() || selectedVehicle == null) showErrors = true else onNextClick()
            }
        )
    }
}

@Composable
private fun VehicleDropdown(
    vehicles: List<Vehicle>,
    selected: Vehicle?,
    onSelected: (Vehicle) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var fieldWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Box(modifier = Modifier.onSizeChanged { fieldWidthPx = it.width }) {
        AppPickerField(
            label = "Vehículo",
            value = selected?.let { "${it.displayPlate} · ${it.brand} ${it.model}" } ?: "",
            placeholder = "Selecciona una placa",
            onClick = { expanded = true },
            trailingIcon = Icons.Filled.KeyboardArrowDown,
            required = true,
            labelFontWeight = FontWeight.Bold,
            isError = isError,
            errorText = "Selecciona el vehículo de la obligación"
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(density) { fieldWidthPx.toDp() })
        ) {
            vehicles.forEach { vehicle ->
                DropdownMenuItem(
                    text = {
                        Column {
                            Text(vehicle.displayPlate, fontWeight = FontWeight.Bold, color = NavyPrimary)
                            Text("${vehicle.brand} ${vehicle.model}", fontSize = 12.sp, color = TextSecondary)
                        }
                    },
                    onClick = {
                        onSelected(vehicle)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DueDateStep(
    dueDate: SimpleDate,
    onDueDateChange: (SimpleDate) -> Unit,
    onSaveClick: () -> Unit
) {
    StepWithBottomButton(buttonText = "Guardar fecha", onButtonClick = onSaveClick) {

        MonthCalendar(selected = dueDate, onDateSelected = onDueDateChange, minDate = SimpleDate.today())

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Fecha seleccionada:",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = dueDate.format(separator = " / "),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary,
                modifier = Modifier
                    .background(AccentBlueLight, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}


@Composable
private fun AlarmStep(
    name: String,
    vehicle: Vehicle?,
    dueDate: SimpleDate,
    alarmDate: SimpleDate,
    onAlarmDateChange: (SimpleDate) -> Unit,
    hour: Int,
    minute: Int,
    onTimeChange: (Int, Int) -> Unit,
    onCreateClick: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }

    val today = SimpleDate.today()
    val alarmError = when {
        alarmDate > dueDate -> "La alarma debe ser antes del vencimiento"
        alarmDate < today -> "La fecha de la alarma ya pasó"
        else -> null
    }

    if (showDatePicker) {
        CalendarDialog(
            initial = alarmDate,
            maxDate = dueDate,
            onDismiss = { showDatePicker = false },
            onDateSelected = {
                onAlarmDateChange(it)
                showDatePicker = false
            }
        )
    }
    if (showTimePicker) {
        TimePickerDialog(
            initialHour = hour,
            initialMinute = minute,
            onDismiss = { showTimePicker = false },
            onConfirm = { h, m ->
                onTimeChange(h, m)
                showTimePicker = false
            }
        )
    }

    StepWithBottomButton(
        buttonText = "Crear alarma",
        onButtonClick = { if (alarmError != null) showErrors = true else onCreateClick() }
    ) {
        ObligationSummaryCard(name = name, vehicle = vehicle, dueDate = dueDate)

        Spacer(modifier = Modifier.height(24.dp))

        AppPickerField(
            label = "Fecha de la alarma",
            value = alarmDate.format(),
            onClick = { showDatePicker = true },
            trailingIcon = Icons.Outlined.CalendarMonth,
            labelFontWeight = FontWeight.Bold,
            helperText = "Te avisaremos con anticipación al vencimiento legal.",
            isError = showErrors && alarmError != null,
            errorText = alarmError.orEmpty()
        )

        Spacer(modifier = Modifier.height(20.dp))

        AppPickerField(
            label = "Hora",
            value = formatTime(hour, minute),
            onClick = { showTimePicker = true },
            trailingIcon = Icons.Outlined.Schedule,
            labelFontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ObligationSummaryCard(name: String, vehicle: Vehicle?, dueDate: SimpleDate) {
    val daysLeft = SimpleDate.today().daysUntil(dueDate)
    val (statusText, statusColor, statusBackground) = when {
        daysLeft < 0 -> Triple("Vencida", ErrorRed, ErrorRedLight)
        daysLeft <= 30 -> Triple("Por vencer", WarningBrown, WarningAmberLight)
        else -> Triple("Al día", SuccessGreen, SuccessGreenLight)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = SurfaceMuted,
        border = BorderStroke(1.dp, OutlineGray)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(AccentBlueLight, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.NoteAdd,
                    contentDescription = null,
                    tint = AccentBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Text(
                    text = "$name - vence ${dueDate.format()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "Vehículo: ${vehicle?.displayPlate.orEmpty()}",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
            StatusTag(text = statusText, color = statusColor, background = statusBackground)
        }
    }
}

@Composable
private fun StatusTag(text: String, color: Color, background: Color) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = Modifier
            .background(background, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
private fun CalendarDialog(
    initial: SimpleDate,
    maxDate: SimpleDate,
    onDismiss: () -> Unit,
    onDateSelected: (SimpleDate) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(12.dp)) {
                MonthCalendar(
                    selected = initial,
                    onDateSelected = onDateSelected,
                    minDate = SimpleDate.today(),
                    maxDate = maxDate
                )
                TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
                    Text("Cancelar", color = AccentBlue)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    val state = rememberTimePickerState(initialHour = initialHour, initialMinute = initialMinute, is24Hour = false)
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text("Hora de la alarma", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
        text = { TimePicker(state = state) },
        confirmButton = {
            TextButton(onClick = { onConfirm(state.hour, state.minute) }) {
                Text("Aceptar", color = AccentBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar", color = TextSecondary) }
        }
    )
}

private fun formatTime(hour: Int, minute: Int): String {
    val hour12 = if (hour % 12 == 0) 12 else hour % 12
    return "%02d:%02d %s".format(hour12, minute, if (hour < 12) "a.m." else "p.m.")
}

@Composable
private fun StepWithBottomButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            content()
        }

        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            HorizontalDivider(color = OutlineGray)
            PrimaryButton(
                text = buttonText,
                onClick = onButtonClick,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }
    }
}
