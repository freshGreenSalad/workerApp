package com.tamaki.workerapp.data.viewModel

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.room.YourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val repository: YourRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private suspend fun read(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]!!
    }

    suspend fun getWorkerProfile(): WorkerProfile {
        return repository.getWorkerProfile(read("email"))
    }

    suspend fun deleteAccount(){
        repository.deleteAccount()
    }

    //------------------------------------------------------------------------------------------------
    private val _state = MutableStateFlow(WorkerProfileViewState())

    val state: StateFlow<WorkerProfileViewState>
        get() = _state

    private val drawerState = MutableStateFlow(DrawerState(initialValue = DrawerValue.Closed))

    private val stateHolder = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                drawerState,
                stateHolder
            ) { drawerState,stateHolder ->
                WorkerProfileViewState(
                    drawerState = drawerState,
                    stateHolder = stateHolder
                )
            }.catch { throwable ->
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    suspend fun deleteAllFromDataStore() {
        dataStore.edit { it.clear()}
    }
    //-------------------------------------------------------------------------------------------------------
}

data class WorkerProfileViewState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
    val stateHolder: String = ""
)