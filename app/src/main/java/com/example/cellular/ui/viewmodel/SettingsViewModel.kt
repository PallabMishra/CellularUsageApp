package com.example.cellular.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _reminderEnabled = MutableStateFlow(false)
    val reminderEnabled: StateFlow<Boolean> = _reminderEnabled

    fun setReminderEnabled(enabled: Boolean) {
        _reminderEnabled.value = enabled
    }
}
