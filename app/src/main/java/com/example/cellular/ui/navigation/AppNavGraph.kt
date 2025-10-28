package com.example.cellular.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cellular.ui.screens.DashboardScreen
import com.example.cellular.ui.screens.PlansScreen
import com.example.cellular.ui.screens.SettingsScreen
import com.example.cellular.ui.viewmodel.DashboardViewModel
import com.example.cellular.ui.viewmodel.PlansViewModel
import com.example.cellular.ui.viewmodel.SettingsViewModel
import com.example.cellular.util.NotificationHelper

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            val vm: DashboardViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsStateWithLifecycle()

            DashboardScreen(
                state = uiState,
                onSubscribeClick = {
                    navController.navigate(Screen.Plans.route)
                },
                onOpenPlans = {
                    navController.navigate(Screen.Plans.route)
                }
            )
        }

        composable(Screen.Plans.route) {
            val vm: PlansViewModel = hiltViewModel()
            val plans by vm.plans.collectAsStateWithLifecycle()

            PlansScreen(
                plans = plans
            )
        }

        composable(Screen.Settings.route) {
            val vm: SettingsViewModel = hiltViewModel()
            val reminderEnabled by vm.reminderEnabled.collectAsStateWithLifecycle()
            val context = LocalContext.current

            // Launcher that will ask for POST_NOTIFICATIONS and then run callback
            val requestNotifPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { granted ->
                    if (granted) {
                        // User said yes -> now actually show the notification
                        NotificationHelper.showLowBalanceReminder(
                            context = context,
                            renewalDate = "Nov 1, 2025",
                            balance = 82.5
                        )
                    }

                }
            )

            SettingsScreen(
                reminderEnabled = reminderEnabled,
                onReminderToggle = { enabled ->
                    vm.setReminderEnabled(enabled)

                    if (enabled) {
                        // We only trigger a notification when switching ON.
                        // For OFF we don't do anything.

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val hasPermission =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED

                            if (hasPermission) {
                                // Already allowed → show notification now
                                NotificationHelper.showLowBalanceReminder(
                                    context = context,
                                    renewalDate = "Nov 1, 2025",
                                    balance = 82.5
                                )
                            } else {
                                // Ask for runtime permission
                                requestNotifPermissionLauncher.launch(
                                    Manifest.permission.POST_NOTIFICATIONS
                                )
                            }
                        } else {
                            // On Android 12 and below, no runtime permission required
                            NotificationHelper.showLowBalanceReminder(
                                context = context,
                                renewalDate = "Nov 1, 2025",
                                balance = 82.5
                            )
                        }
                    }
                }
            )
        }
    }
}
