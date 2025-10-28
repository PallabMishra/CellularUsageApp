package com.example.cellular.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cellular.data.model.Promo


@Composable
fun PromotionsRow(
    promos: List<Promo>,
    modifier: Modifier = Modifier
) {
    val displayList: List<Promo> =
        if (promos.isNotEmpty()) promos else listOf(
            Promo(
                id = "1",
                title = "Super Saver 499",
                description = "5GB/day • Unlimited calls • Weekend OTT bonus",
                cta = "Know More"
            ),
            Promo(
                id = "2",
                title = "Weekend Data Boost",
                description = "Extra 20GB just for Sat+Sun bingeing",
                cta = "Subscribe"
            ),
            Promo(
                id = "3",
                title = "Max 999 Ultra",
                description = "Truly Unlimited 5G + Intl Roaming Pack",
                cta = "Upgrade"
            ),
            Promo(
                id = "4",
                title = "Night Owl Pack",
                description = "Free data 12AM–6AM, perfect for downloads",
                cta = "Activate"
            ),
            Promo(
                id = "5",
                title = "Roam Plus Intl",
                description = "Travel-ready voice & data in 15 countries",
                cta = "View Rates"
            ),
            Promo(
                id = "6",
                title = "Student Flex",
                description = "High data, low price. ID required.",
                cta = "Apply"
            )
        )

    LazyRow(
        modifier = modifier.padding(top = 4.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(displayList) { promo ->
            GlassPromoCard(
                promo = promo,
                onClick = { /* mock CTA tap */ }
            )
        }
    }
}


@Composable
fun GlassPromoCard(
    promo: Promo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // One infinite transition drives both the bobbing motion and glow pulse
    val infinite = rememberInfiniteTransition(label = "promoFloatAnim")

    // gentle "hover up / hover down"
    val offsetY by infinite.animateFloat(
        initialValue = 0f,
        targetValue = -4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    // glow alpha pulse
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.18f,
        targetValue = 0.32f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    val shape = RoundedCornerShape(16.dp)

    // translucent background fill (glass feel)
    val glassFill = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)

    // subtle border stroke (like frosted edge)
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)

    Box(
        modifier = modifier
            .width(260.dp)
            .height(150.dp) // slightly taller than before
            .graphicsLayer {
                translationY = offsetY
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = 24.dp,
                    shape = shape,
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = glowAlpha),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = glowAlpha)
                )
        )

        // Actual frosted card
        Surface(
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = 12.dp,
                    shape = shape,
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                )
                .clip(shape),
            shape = shape,
            color = glassFill,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = borderColor
            )
        ) {

            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            0f to Color.White.copy(alpha = 0.12f),
                            0.5f to Color.White.copy(alpha = 0.04f),
                            1f to Color.Transparent
                        )
                    )
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(118.dp) // keep content tidy vertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            Text(
                                text = promo.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 16.sp,
                                    lineHeight = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = promo.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 13.sp,
                                lineHeight = 16.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                        )
                    }

                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text(
                            text = promo.cta,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                lineHeight = 15.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}
