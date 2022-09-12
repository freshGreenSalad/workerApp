package com.example.workerapp.data.viewModel

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workerapp.data.room.YourRepository
import com.example.workerapp.ui.homeUi.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class workerviewmodel @Inject constructor(
    private val repository: YourRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    @OptIn(ExperimentalMaterial3Api::class)
    private val drawerState = MutableStateFlow(DrawerState(initialValue = DrawerValue.Closed))
    private val secondvaluetostoperroroninit = MutableStateFlow(DrawerState(initialValue = DrawerValue.Closed))

    private val _state = MutableStateFlow(WorkerProfileViewState())

    val state: StateFlow<WorkerProfileViewState>
        get() = _state
    init {
        viewModelScope.launch {
            combine(
                drawerState,
                secondvaluetostoperroroninit
            ) { drawerState,secondvaluetostoperroroninit ->
                WorkerProfileViewState(
                    drawerState = drawerState,
                    secondvaluetostoperroroninit
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }
    suspend fun delete(context: Context) {
        dataStore.edit { it.clear()}
    }

}

data class WorkerProfileViewState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
    val secondvaluetostoperroroninit: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)