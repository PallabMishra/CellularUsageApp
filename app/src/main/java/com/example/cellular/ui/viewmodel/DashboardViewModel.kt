package com.example.cellular.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cellular.data.model.Plan
import com.example.cellular.data.model.Promo
import com.example.cellular.data.model.UsageStats
import com.example.cellular.data.repository.CellularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DashboardUiState(
    val usage: UsageStats = UsageStats(
        dataUsedGb = 0.0,
        dataTotalGb = 0.0,
        minutesUsed = 0,
        minutesTotal = 0,
        smsUsed = 0,
        smsTotal = 0,
        balance = 0.0,
        renewalDate = "-"
    ),
    val promos: List<Promo> = emptyList(),
    val topPlans: List<Plan> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: CellularRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> =
        combine(
            repo.usageStats,
            repo.promos,
            repo.plans
        ) { usage, promos, plans ->
            DashboardUiState(
                usage = usage,
                promos = promos,
                topPlans = plans.take(3)
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DashboardUiState(
                usage = repo.usageStats.value,
                promos = repo.promos.value,
                topPlans = repo.plans.value.take(3)
            )
        )
}
