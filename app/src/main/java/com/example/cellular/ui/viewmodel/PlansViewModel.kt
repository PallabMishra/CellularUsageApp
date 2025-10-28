package com.example.cellular.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cellular.data.model.Plan
import com.example.cellular.data.repository.CellularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val repo: CellularRepository
) : ViewModel() {
    val plans: StateFlow<List<Plan>> = repo.plans
}
