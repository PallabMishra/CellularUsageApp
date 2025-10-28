/*PALLAB KANTI MISHRA*/
package com.example.cellular.data.model

data class UsageStats(
    val dataUsedGb: Double,
    val dataTotalGb: Double,
    val minutesUsed: Int,
    val minutesTotal: Int,
    val smsUsed: Int,
    val smsTotal: Int,
    val balance: Double,
    val renewalDate: String
)
