package com.micarroaldia.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    title: String,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    centered: Boolean = false,
    showDivider: Boolean = true
) {
    Column {
        val navigationIcon = @Composable {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
        }
        val actions = @Composable { LogoutCircleButton(onClick = onLogoutClick) }

        if (centered) {
            CenterAlignedTopAppBar(
                title = { Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = navigationIcon,
                actions = { actions() },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        } else {
            TopAppBar(
                title = { Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = navigationIcon,
                actions = { actions() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
        if (showDivider) HorizontalDivider(color = OutlineGray)
    }
}

@Composable
fun LogoutCircleButton(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.padding(end = 12.dp).size(34.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, OutlineGray)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text("salir", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
        }
    }
}
