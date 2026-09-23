package com.micarroaldia.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micarroaldia.app.ui.theme.ErrorRed
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray
import com.micarroaldia.app.ui.theme.TextSecondary

@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    required: Boolean = false,
    labelFontWeight: FontWeight? = null,
    helperText: String = "",
    trailingContent: (@Composable () -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                append(label)
                if (required) withStyle(SpanStyle(color = ErrorRed)) { append(" *") }
            },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = labelFontWeight,
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            placeholder = { Text(placeholder, color = TextSecondary) },
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = capitalization
            ),
            trailingIcon = trailingContent,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = OutlineGray,
                errorBorderColor = ErrorRed
            )
        )
        if (isError && errorText.isNotBlank()) {
            Text(
                text = errorText,
                color = ErrorRed,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        } else if (helperText.isNotBlank()) {
            Text(
                text = helperText,
                color = TextSecondary,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AppPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorText: String = ""
) {
    var isVisible by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            placeholder = { Text(placeholder, color = TextSecondary) },
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        imageVector = if (isVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (isVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = TextSecondary
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NavyPrimary,
                unfocusedBorderColor = OutlineGray,
                errorBorderColor = ErrorRed
            )
        )
        if (isError && errorText.isNotBlank()) {
            Text(
                text = errorText,
                color = ErrorRed,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
    ) {
        Text(text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, color = NavyPrimary)
    }
}
