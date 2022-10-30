package com.tamaki.workerapp.ui.screens.supervisor.supervisorHome

import android.content.Context
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.datastore.DatastoreInterface
import com.tamaki.workerapp.data.repositorys.RepositoryInterface

val Context.dataStore by preferencesDataStore("user_preferences")

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class SupervisorViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStore: DatastoreInterface
) : ViewModel() {

    suspend fun getSupervisorProfile(): SupervisorProfile {
        return repository.getSupervisorProfile()
    }

    suspend fun getListOfWorkerAccountsForSupervisor(): List<WorkerProfile> {
        val list = repository.getListOfWorkerAccountsForSupervisor()
        return  list
    }

    suspend fun getWorkerByEmail(email: String): WorkerProfile {
        return repository.getWorkerProfile(email)
    }

    suspend fun deleteAccount(){
        repository.deleteAccount()
    }

    suspend fun getWorkerDriversLicence(email: String):DriversLicence{
        return repository.getWorkerDriversLicence(email)
    }

    //aws functions --------------------------
    //dynamodb functions

    suspend fun login(authRequest: ProfileLoginAuthRequest) {
        val result = repository.login(authRequest)
        resultChannel.send(result)
    }

    suspend fun deleteAllFromDataStore() {
        dataStore.deleteAccount()
    }

    //Auth state----------------------------------------------------------------------------------------------
    private val resultChannel = Channel<authResult<Boolean?>>()

    val authResults = resultChannel.receiveAsFlow()

    //---------------------------------------------------------------------------------------------------------
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    private val drawerState = MutableStateFlow(DrawerState(initialValue = Closed))

    private val homeBottomAppBarTabs = MutableStateFlow(HomeBottomAppBarTabs.values().asList())

    private val selectedHomeBottomAppBarTab = MutableStateFlow(HomeBottomAppBarTabs.Home)

    private val savedWorkers = MutableStateFlow(mutableStateListOf<String>())

    private val workerList = MutableStateFlow(mutableStateListOf<WorkerProfile>())

    private val workerListSize = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            val list = getListOfWorkerAccountsForSupervisor()
            workerListSize.value = list.size
            workerList.value = list.toMutableStateList()
        }
    }

    init {
        viewModelScope.launch {
            combine(
                homeBottomAppBarTabs,
                selectedHomeBottomAppBarTab,
                drawerState,
                savedWorkers,
                workerList,
                workerListSize
            ) { homeBottomAppBarTabs, SelectedHomeBottomAppBarTab, drawerState, savedWorkers, _workerList, _workerListSize ->
                HomeViewState(
                    homeAppBarTabs = homeBottomAppBarTabs,
                    selectedHomeBarTab = SelectedHomeBottomAppBarTab,
                    drawerState = drawerState,
                    savedWorkers = savedWorkers,
                    workerList = _workerList,
                    workerListSize = _workerListSize
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    fun <T1, T2, T3, T4, T5, T6, R> combine(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        transform: suspend (T1, T2, T3, T4, T5, T6) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple),
    ) { t1, t2 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
        )
    }

fun removeFromWatchlist(email: String) {
    savedWorkers.value.remove(email)
}

fun addToWatchList(email: String) {
    savedWorkers.value.add(email)
}

fun onClickHomeBottomAppTab(tab: HomeBottomAppBarTabs) {
    selectedHomeBottomAppBarTab.value = tab
}
}

data class HomeViewState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val homeAppBarTabs: List<HomeBottomAppBarTabs> = emptyList(),
    val selectedHomeBarTab: HomeBottomAppBarTabs = HomeBottomAppBarTabs.Home,
    val drawerState: DrawerState = DrawerState(initialValue = Closed),
    val savedWorkers: MutableList<String> = mutableListOf(),
    val workerList: List<WorkerProfile> = mutableListOf(),
    val workerListSize: Int = 0
)

enum class HomeBottomAppBarTabs {
    Home, Watchlisted, Search
}