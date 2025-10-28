package com.example.cellular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cellular.ui.navigation.AppNavGraph
import com.example.cellular.ui.navigation.Screen
import com.example.cellular.ui.theme.CellularTheme
import com.example.cellular.util.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)

        setContent {
            CellularTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        FloatingGlassBottomBar(
                            navController = navController,
                            items = listOf(
                                BottomNavItem(Screen.Dashboard, Icons.Outlined.Home),
                                BottomNavItem(Screen.Plans, Icons.Outlined.ShoppingCart),
                                BottomNavItem(Screen.Settings, Icons.Outlined.Settings)
                            )
                        )
                    },
                    // content goes edge-to-edge; we'll pad ourselves
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector
)


@Composable
fun FloatingGlassBottomBar(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        GlassPill {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items.forEachIndexed { index, item ->
                    val selected = currentRoute == item.screen.route

                    val iconScale by animateFloatAsState(
                        targetValue = if (selected) 1.15f else 1f,
                        animationSpec = tween(
                            durationMillis = 220,
                            easing = FastOutSlowInEasing
                        ),
                        label = "iconScale-$index"
                    )

                    val itemPillColor by animateColorAsState(
                        targetValue = if (selected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.92f)
                        else
                            Color.Transparent,
                        animationSpec = tween(
                            durationMillis = 220,
                            easing = FastOutSlowInEasing
                        ),
                        label = "itemPillColor-$index"
                    )

                    val iconTint by animateColorAsState(
                        targetValue = if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(
                            durationMillis = 220,
                            easing = FastOutSlowInEasing
                        ),
                        label = "iconTint-$index"
                    )

                    val labelTint by animateColorAsState(
                        targetValue = if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(
                            durationMillis = 220,
                            easing = FastOutSlowInEasing
                        ),
                        label = "labelTint-$index"
                    )

                    Surface(
                        modifier = Modifier
                            .height(44.dp)
                            .width(88.dp) // widened so "Settings" stays one line
                            .clip(RoundedCornerShape(18.dp))
                            .clickable {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        color = itemPillColor,
                        contentColor = Color.Unspecified,
                        shape = RoundedCornerShape(18.dp),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.screen.label,
                                tint = iconTint,
                                modifier = Modifier
                                    .size(20.dp)
                                    .graphicsLayer {
                                        scaleX = iconScale
                                        scaleY = iconScale
                                    }
                            )

                            AnimatedVisibility(
                                visible = selected,
                                enter = fadeIn(animationSpec = tween(150)),
                                exit = fadeOut(animationSpec = tween(150))
                            ) {
                                Text(
                                    text = item.screen.label,
                                    color = labelTint,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        lineHeight = 14.sp
                                    ),
                                    maxLines = 1 // keep "Settings" from wrapping
                                )
                            }
                        }
                    }

                    if (index < items.lastIndex) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
    }
}


@Composable
private fun GlassPill(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
    val glassColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)

    Surface(
        modifier = modifier
            .shadow(
                elevation = 20.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.25f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            )
            .clip(shape),
        shape = shape,
        color = glassColor,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
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
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
