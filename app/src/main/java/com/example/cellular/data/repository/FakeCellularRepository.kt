/*PALLAB KANTI MISHRA*/
package com.example.cellular.data.repository

import com.example.cellular.data.model.Plan
import com.example.cellular.data.model.Promo
import com.example.cellular.data.model.UsageStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCellularRepository @Inject constructor() : CellularRepository {

    private val _usageStats = MutableStateFlow(
        UsageStats(
            dataUsedGb = 2.3,
            dataTotalGb = 5.0,
            minutesUsed = 340,
            minutesTotal = 1000,
            smsUsed = 120,
            smsTotal = 500,
            balance = 82.5,
            renewalDate = "Nov 1, 2025"
        )
    )
    override val usageStats: StateFlow<UsageStats> = _usageStats

    private val _promos = MutableStateFlow(
        listOf(
            Promo(
                id = "promo_super_saver",
                title = "Super Saver 499",
                description = "Get 5GB/day + Unlimited calls for just ₹499. Weekend binge bonus included!",
                cta = "Know More"
            ),
            Promo(
                id = "promo_weekend_boost",
                title = "Weekend Data Boost",
                description = "Extra 20GB on Sat-Sun for heavy streaming.",
                cta = "Subscribe"
            )
        )
    )
    override val promos: StateFlow<List<Promo>> = _promos

    private val _plans = MutableStateFlow(
        listOf(
            Plan(
                name = "Super 299",
                price = "₹299",
                data = "3GB/day",
                minutes = "1000",
                sms = "100"
            ),
            Plan(
                name = "Max 499",
                price = "₹499",
                data = "5GB/day",
                minutes = "Unlimited",
                sms = "500"
            ),
            Plan(
                name = "Power 799",
                price = "₹799",
                data = "10GB/day",
                minutes = "Unlimited",
                sms = "1000"
            )
        )
    )
    override val plans: StateFlow<List<Plan>> = _plans

    override fun consumeData(mb: Int) {
        _usageStats.update { current ->
            val usedMb = current.dataUsedGb * 1024 + mb
            current.copy(
                dataUsedGb = (usedMb / 1024.0).coerceAtMost(current.dataTotalGb)
            )
        }
    }

    override fun consumeMinutes(min: Int) {
        _usageStats.update { current ->
            val used = current.minutesUsed + min
            current.copy(
                minutesUsed = used.coerceAtMost(current.minutesTotal)
            )
        }
    }

    override fun consumeSms(count: Int) {
        _usageStats.update { current ->
            val used = current.smsUsed + count
            current.copy(
                smsUsed = used.coerceAtMost(current.smsTotal)
            )
        }
    }
}
