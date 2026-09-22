package com.micarroaldia.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micarroaldia.app.ui.theme.AccentBlue
import com.micarroaldia.app.ui.theme.AccentBlueLight
import com.micarroaldia.app.ui.theme.TextSecondary

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    subtitle: String = "GESTIÓN DE TUS VEHÍCULOS",
    iconSize: Int = 56
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(iconSize.dp)
                .background(AccentBlueLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.DirectionsCar,
                contentDescription = null,
                tint = AccentBlue,
                modifier = Modifier.size((iconSize * 0.5).dp)
            )
        }
        Text(
            text = "MiCarro al Día",
            modifier = Modifier.padding(top = 12.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = subtitle,
            modifier = Modifier.padding(top = 2.dp),
            fontSize = 11.sp,
            letterSpacing = 1.sp,
            color = TextSecondary
        )
    }
}
