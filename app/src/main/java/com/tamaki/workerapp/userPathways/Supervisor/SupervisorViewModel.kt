package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome

import android.content.Context
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
import androidx.datastore.preferences.preferencesDataStore
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.datastore.DatastoreInterface
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.data.utility.Combine
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SuccessStatus
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.WorkerDate
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.WorkerSearchQuery
import com.tamaki.workerapp.userPathways.Supervisor.SupervisorRepository
import kotlinx.coroutines.channels.Channel

val Context.dataStore by preferencesDataStore("user_preferences")

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class SupervisorViewModel @Inject constructor(
    private val repository: RepositoryInterface,
    private val dataStore: DatastoreInterface,
    private val supervisorRepository: SupervisorRepository
) : ViewModel() {

    fun hireWorker() {
        val dateToBeginWork = date.value
        val workerEmail = currentSelectedWorker.value.email
        viewModelScope.launch {
            updateHireWorkerSuccessStatus(
                supervisorRepository.hireWorker(
                    workerEmail,
                    dateToBeginWork
                )
            )
        }
    }

    suspend fun updateHireWorkerSuccessStatus(status: SuccessStatus) = HireChannel.send(status)

    suspend fun getSupervisorProfile(): SupervisorProfile = repository.getSupervisorProfile()

    suspend fun getListOfWorkerAccountsForSupervisor(): List<WorkerProfile> = repository.getListOfWorkerAccountsForSupervisor()

    suspend fun getWorkerByEmail(email: String): WorkerProfile = repository.getWorkerProfile(email)

    suspend fun deleteAccount() = repository.deleteAccount()

    suspend fun getWorkerDriversLicence(email: String): DriversLicence = repository.getWorkerDriversLicence(email)

    suspend fun deleteAllFromDataStore() = dataStore.deleteAccount()

    suspend fun getListOfHiredWorkers(): List<WorkerProfile> =
        supervisorRepository.getListOfHiredWorkers()

    fun searchForWorkers() {
        viewModelScope.launch {
            val searchQuery = WorkerSearchQuery(
                lowerBound = range.value.start.toInt(),
                upperBound = range.value.endInclusive.toInt(),
                experience = experience.value
            )
            searchedForWorkerList.value =
                supervisorRepository.searchForWorker(searchQuery).toMutableStateList()
        }
    }
    private val HireChannel = Channel<SuccessStatus>()

    val HireChannelStatus = HireChannel.receiveAsFlow()

    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    private val drawerState = MutableStateFlow(DrawerState(initialValue = Closed))

    private val homeBottomAppBarTabs = MutableStateFlow(HomeBottomAppBarTabs.values().asList())

    private val selectedHomeBottomAppBarTab = MutableStateFlow(HomeBottomAppBarTabs.Home)

    private val savedWorkers = MutableStateFlow(mutableStateListOf<String>())

    private val workerList = MutableStateFlow(mutableStateListOf<WorkerProfile>())

    private val workerListSize = MutableStateFlow(0)

    private val hiredWorkerList = MutableStateFlow(mutableStateListOf<WorkerProfile>())

    private val hiredWorkerListSize = MutableStateFlow(0)

    private val currentSelectedWorker = MutableStateFlow(WorkerProfile("", "", "", "", 45))

    private val date = MutableStateFlow(WorkerDate(0, 0, 0))

    private val searchedForWorkerList = MutableStateFlow(mutableStateListOf<WorkerProfile>())

    private val range = MutableStateFlow(0f..10f)

    private val experience = MutableStateFlow("")

    init {
        viewModelScope.launch {
            val displayListOfWorkers = getListOfWorkerAccountsForSupervisor()
            workerListSize.value = displayListOfWorkers.size
            workerList.value = displayListOfWorkers.toMutableStateList()

            val listOfHiredWorkers = getListOfHiredWorkers()
            hiredWorkerListSize.value = listOfHiredWorkers.size
            hiredWorkerList.value = listOfHiredWorkers.toMutableStateList()

            Combine().thirteen(
                homeBottomAppBarTabs,
                selectedHomeBottomAppBarTab,
                drawerState,
                savedWorkers,
                workerList,
                workerListSize,
                currentSelectedWorker,
                date,
                hiredWorkerListSize,
                hiredWorkerList,
                range,
                experience,
                searchedForWorkerList
            ) { homeBottomAppBarTabs, SelectedHomeBottomAppBarTab, drawerState,
                savedWorkers, _workerList, _workerListSize, currentSelectedWorker,
                date, hiredWorkerListSize, hiredWorkerList, range,experience,searchedForWorkerList  ->
                HomeViewState(
                    homeAppBarTabs = homeBottomAppBarTabs,
                    selectedHomeBarTab = SelectedHomeBottomAppBarTab,
                    drawerState = drawerState,
                    savedWorkers = savedWorkers,
                    workerList = _workerList,
                    workerListSize = _workerListSize,
                    currentSelectedWorker = currentSelectedWorker,
                    date = date,
                    hiredWorkerListSize = hiredWorkerListSize,
                    hiredWorkerList = hiredWorkerList,
                    range = range,
                    experience = experience,
                    searchedForWorkerList = searchedForWorkerList
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    fun removeFromWatchlist(email: String) = savedWorkers.value.remove(email)

    fun addToWatchList(email: String) = savedWorkers.value.add(email)

    fun onClickHomeBottomAppTab(tab: HomeBottomAppBarTabs) {
        selectedHomeBottomAppBarTab.value = tab
    }

    fun WorkerInWatchlist(email: String): Boolean = email in savedWorkers.value

    fun setCurrentSelectedWorker(WorkerProfile: WorkerProfile) {
        currentSelectedWorker.value = WorkerProfile
    }

    fun updateChosenDate(day:Int, month:Int, year:Int){
        date.value = WorkerDate(day = day, month = month, year = year)
    }
    fun updateRange(newRange:ClosedFloatingPointRange<Float>){
        range.value = newRange
    }

}

data class HomeViewState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val homeAppBarTabs: List<HomeBottomAppBarTabs> = HomeBottomAppBarTabs.values().asList(),
    val selectedHomeBarTab: HomeBottomAppBarTabs = HomeBottomAppBarTabs.Home,
    val drawerState: DrawerState = DrawerState(initialValue = Closed),
    val savedWorkers: MutableList<String> = mutableListOf(),
    val workerList: List<WorkerProfile> = mutableListOf(),
    val workerListSize: Int = 0,
    val currentSelectedWorker: WorkerProfile = WorkerProfile("","","","",45),
    val date: WorkerDate = WorkerDate(0,0,0),
    val hiredWorkerListSize: Int = 0,
    val hiredWorkerList:List<WorkerProfile> = emptyList(),
    val range:ClosedFloatingPointRange<Float> = 0f..10f,
    val experience: String = "",
    val searchedForWorkerList: List<WorkerProfile> = emptyList()
)

enum class HomeBottomAppBarTabs {
    Home, Watchlisted, Search
}
