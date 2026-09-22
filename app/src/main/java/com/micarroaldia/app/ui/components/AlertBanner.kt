package com.micarroaldia.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.micarroaldia.app.ui.theme.ErrorRed
import com.micarroaldia.app.ui.theme.ErrorRedLight

@Composable
fun ErrorBanner(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ErrorRedLight)
            .padding(12.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            tint = ErrorRed
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(text = title, color = ErrorRed, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(text = description, color = ErrorRed, style = MaterialTheme.typography.labelSmall)
        }
    }
}
