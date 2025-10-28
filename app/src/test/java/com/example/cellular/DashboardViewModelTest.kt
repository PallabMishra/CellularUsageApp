package com.example.cellular

import com.example.cellular.data.repository.FakeCellularRepository
import com.example.cellular.ui.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DashboardViewModelTest {

    // 👇 this installs a fake Dispatchers.Main for each test
    @get:Rule
    @OptIn(ExperimentalCoroutinesApi::class)
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun dashboardViewModel_exposesRepositoryUsage() {
        val repo = FakeCellularRepository()
        val vm = DashboardViewModel(repo)

        val state = vm.uiState.value

        // usage
        assertEquals(2.3, state.usage.dataUsedGb, 0.0001)
        assertEquals(5.0, state.usage.dataTotalGb, 0.0001)
        assertEquals(340, state.usage.minutesUsed)
        assertEquals(1000, state.usage.minutesTotal)
        assertEquals(120, state.usage.smsUsed)
        assertEquals(500, state.usage.smsTotal)
        assertEquals("Nov 1, 2025", state.usage.renewalDate)

        // plans
        assertEquals(3, state.topPlans.size)
        assertEquals("Super 299", state.topPlans[0].name)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun dashboardViewModel_updatesWhenRepositoryUsageChanges() =
        runTest(mainDispatcherRule.testDispatcher) {

            // Arrange
            val repo = FakeCellularRepository()
            val vm = DashboardViewModel(repo)

            val beforeUsed = vm.uiState.value.usage.dataUsedGb

            // Act: simulate usage increase
            repo.consumeData(mb = 512)

            // Let coroutines in viewModelScope collect the new flow values
            advanceUntilIdle()

            val afterUsed = vm.uiState.value.usage.dataUsedGb

            // Assert: usage went up
            assertTrue(
                "Expected dataUsedGb to increase after consumeData(); before=$beforeUsed after=$afterUsed",
                afterUsed > beforeUsed
            )
        }
}
