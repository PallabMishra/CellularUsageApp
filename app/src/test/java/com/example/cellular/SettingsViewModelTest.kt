package com.example.cellular

import com.example.cellular.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsViewModelTest {

    @Test
    fun defaultReminder_isDisabled() = runTest {
        val vm = SettingsViewModel()
        assertEquals(false, vm.reminderEnabled.value)
    }

    @Test
    fun setReminderEnabled_updatesState() = runTest {
        val vm = SettingsViewModel()

        vm.setReminderEnabled(true)
        assertEquals(true, vm.reminderEnabled.value)

        vm.setReminderEnabled(false)
        assertEquals(false, vm.reminderEnabled.value)
    }
}
