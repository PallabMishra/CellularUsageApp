package com.example.cellular.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cellular.data.model.UsageStats
import com.example.cellular.ui.theme.CellularThemeExt

@Composable
fun UsageCard(
    usage: UsageStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            UsageRow(
                label = "Data Used",
                usedText = String.format("%.1f GB", usage.dataUsedGb),
                totalText = String.format("%.0f GB", usage.dataTotalGb),
                progressColor = CellularThemeExt.usageDataColor,
                progress = (usage.dataUsedGb / usage.dataTotalGb).toFloat()
            )

            UsageRow(
                label = "Minutes",
                usedText = "${usage.minutesUsed}",
                totalText = "${usage.minutesTotal}",
                progressColor = CellularThemeExt.usageMinutesColor,
                progress = (usage.minutesUsed.toFloat() / usage.minutesTotal.toFloat())
            )

            UsageRow(
                label = "SMS",
                usedText = "${usage.smsUsed}",
                totalText = "${usage.smsTotal}",
                progressColor = CellularThemeExt.usageSmsColor,
                progress = (usage.smsUsed.toFloat() / usage.smsTotal.toFloat())
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Remaining Balance: ₹${"%.2f".format(usage.balance)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Renews on ${usage.renewalDate}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun UsageRow(
    label: String,
    usedText: String,
    totalText: String,
    progressColor: Color,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        label = "usageProgress"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$usedText / $totalText",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(MaterialTheme.shapes.small),
            color = progressColor,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
