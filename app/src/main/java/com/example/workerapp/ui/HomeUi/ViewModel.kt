package com.example.workerapp.ui.HomeUi

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workerapp.Data.Room.Workers
import com.example.workerapp.Data.Room.YourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.material3.DrawerValue.Closed

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: YourRepository
): ViewModel() {

    private val homeBottomAppBarTabs = MutableStateFlow(HomeBottomAppBarTabs.values().asList())

    private val SelectedHomeBottomAppBarTab = MutableStateFlow(HomeBottomAppBarTabs.Home)

    @OptIn(ExperimentalMaterial3Api::class)
    private val drawerState = MutableStateFlow(DrawerState(initialValue = Closed))

    private val showPlus = MutableStateFlow(true)

    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state




    init {
        viewModelScope.launch {
            combine(
                showPlus,
                homeBottomAppBarTabs,
                SelectedHomeBottomAppBarTab,
                drawerState
            ) { showPlus, homeBottomAppBarTabs,SelectedHomeBottomAppBarTab,drawerState ->
                HomeViewState(
                    showPlus = showPlus,
                    homeAppBarTabs = homeBottomAppBarTabs,
                    selectedHomeBarTab = SelectedHomeBottomAppBarTab,
                    drawerState = drawerState,
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    fun GetWorkerFlow(): Flow<List<Workers>> = repository.getWorkers()

    suspend fun addworker(workers: Workers) {
        Log.d("button", "worker added")
        repository.upsert(workers)
    }

    fun onClickWatchlist(){
        showPlus.value = !showPlus.value
    }

    fun onClickHomeBottomAppTab (tab: HomeBottomAppBarTabs){
        SelectedHomeBottomAppBarTab.value = tab
    }

}

data class HomeViewState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val showPlus: Boolean = true,
    val homeAppBarTabs: List<HomeBottomAppBarTabs> = emptyList(),
    val selectedHomeBarTab: HomeBottomAppBarTabs = HomeBottomAppBarTabs.Home,
    val drawerState: DrawerState = DrawerState(initialValue = Closed),
)

enum class HomeBottomAppBarTabs{
    Home, Watchlisted, Search
}