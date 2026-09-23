package com.micarroaldia.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micarroaldia.app.data.SimpleDate
import com.micarroaldia.app.ui.theme.AccentBlue
import com.micarroaldia.app.ui.theme.NavyPrimary
import com.micarroaldia.app.ui.theme.OutlineGray
import com.micarroaldia.app.ui.theme.SurfaceWhite
import com.micarroaldia.app.ui.theme.TextSecondary

private val monthNames = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
private val weekdayNames = listOf("Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sá")


@Composable
fun MonthCalendar(
    selected: SimpleDate,
    onDateSelected: (SimpleDate) -> Unit,
    modifier: Modifier = Modifier,
    minDate: SimpleDate? = null,
    maxDate: SimpleDate? = null
) {
    var visibleYear by remember { mutableIntStateOf(selected.year) }
    var visibleMonth by remember { mutableIntStateOf(selected.month) }

    fun showMonth(year: Int, month: Int) {
        visibleYear = year + Math.floorDiv(month - 1, 12)
        visibleMonth = Math.floorMod(month - 1, 12) + 1
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = SurfaceWhite,
        border = BorderStroke(1.dp, OutlineGray)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showMonth(visibleYear, visibleMonth - 1) }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Mes anterior")
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CalendarChipDropdown(
                        text = monthNames[visibleMonth - 1],
                        options = monthNames,
                        onOptionSelected = { index -> visibleMonth = index + 1 }
                    )
                    Box(modifier = Modifier.size(6.dp))
                    val years = (SimpleDate.today().year - 1..SimpleDate.today().year + 10).toList()
                    CalendarChipDropdown(
                        text = visibleYear.toString(),
                        options = years.map { it.toString() },
                        onOptionSelected = { index -> visibleYear = years[index] }
                    )
                }
                IconButton(onClick = { showMonth(visibleYear, visibleMonth + 1) }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Mes siguiente")
                }
            }

            HorizontalDivider(color = OutlineGray, modifier = Modifier.padding(vertical = 6.dp))

            Row {
                weekdayNames.forEach { name ->
                    Text(
                        text = name,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f).padding(vertical = 6.dp)
                    )
                }
            }

            val offset = SimpleDate.firstWeekdayOfMonth(visibleYear, visibleMonth)
            val daysInMonth = SimpleDate.daysInMonth(visibleYear, visibleMonth)
            val firstCell = SimpleDate(visibleYear, visibleMonth, 1).plusDays(-offset)
            val weeks = (offset + daysInMonth + 6) / 7

            for (week in 0 until weeks) {
                Row {
                    for (weekday in 0 until 7) {
                        val date = firstCell.plusDays(week * 7 + weekday)
                        val isEnabled = (minDate == null || date >= minDate) && (maxDate == null || date <= maxDate)
                        DayCell(
                            date = date,
                            isCurrentMonth = date.month == visibleMonth,
                            isSelected = date == selected,
                            isEnabled = isEnabled,
                            onClick = {
                                onDateSelected(date)
                                if (date.month != visibleMonth) showMonth(date.year, date.month)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    date: SimpleDate,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(if (isSelected) AccentBlue else SurfaceWhite)
                .clickable(enabled = isEnabled, onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.day.toString(),
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    isSelected -> SurfaceWhite
                    !isEnabled -> OutlineGray
                    isCurrentMonth -> NavyPrimary
                    else -> OutlineGray
                },
                textDecoration = if (!isEnabled && isCurrentMonth) TextDecoration.LineThrough else null
            )
        }
    }
}

@Composable
private fun CalendarChipDropdown(
    text: String,
    options: List<String>,
    onOptionSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, OutlineGray, RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}
