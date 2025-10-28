/*PALLAB KANTI MISHRA*/
package com.example.cellular.data.repository

import com.example.cellular.data.model.Plan
import com.example.cellular.data.model.Promo
import com.example.cellular.data.model.UsageStats
import kotlinx.coroutines.flow.StateFlow

interface CellularRepository {

    val usageStats: StateFlow<UsageStats>
    val promos: StateFlow<List<Promo>>
    val plans: StateFlow<List<Plan>>

    fun consumeData(mb: Int)
    fun consumeMinutes(min: Int)
    fun consumeSms(count: Int)
}
