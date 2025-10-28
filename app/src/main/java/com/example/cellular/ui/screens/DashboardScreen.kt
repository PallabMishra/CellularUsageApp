package com.example.cellular.ui.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cellular.data.model.Plan
import com.example.cellular.ui.components.PlanCard
import com.example.cellular.ui.components.PromotionsRow
import com.example.cellular.ui.components.SectionHeader
import com.example.cellular.ui.components.UsageCard
import com.example.cellular.ui.viewmodel.DashboardUiState

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onSubscribeClick: (Plan) -> Unit,
    onOpenPlans: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                top = 25.dp,
                bottom = 32.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // ---------- HEADER + USAGE SUMMARY CARD ----------
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Screen header row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // circular chip with analytics icon
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Assessment,
                            contentDescription = "Usage summary",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Your Usage",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 22.sp,
                                lineHeight = 26.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = "Plan renews on ${state.usage.renewalDate}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }

                UsageCard(
                    usage = state.usage
                )
            }
        }

        // ---------- PROMOTIONS ----------
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SectionHeader(
                    title = "📢 Promotions"
                )
                PromotionsRow(
                    promos = state.promos
                )
            }
        }

        // ---------- TOP PLANS PREVIEW ----------
        item {
            SectionHeader(
                title = "📦 Available Plans",
            )
        }

        items(state.topPlans) { plan ->
            PlanCard(
                plan = plan,
                onSubscribeClick = onSubscribeClick,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        // ---------- FOOTER ----------
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "© 2025 Mobily. All rights reserved.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(
                    bottom = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }
    }
}
