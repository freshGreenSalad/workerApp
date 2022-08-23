package com.example.workerapp.ui.HomeUi

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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.produceState
import com.example.workerapp.Data.Room.*
import com.example.workerapp.Data.Room.ktor.AWSInterface
import com.example.workerapp.Data.Room.ktor.WorkerCasheMap
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: YourRepository,
): ViewModel() {

    suspend fun getworker(key:Int): String{
        val index = key + 1
        val cashedcharacter = WorkerCasheMap.workerCashMap[index]
        if (cashedcharacter != null){
            return cashedcharacter
        }else {
            val response = repository.getworkerstring(index)
            WorkerCasheMap.workerCashMap[index] = response
            return response
        }
    }

    private val homeBottomAppBarTabs = MutableStateFlow(HomeBottomAppBarTabs.values().asList())

    private val SelectedHomeBottomAppBarTab = MutableStateFlow(HomeBottomAppBarTabs.Home)

    @OptIn(ExperimentalMaterial3Api::class)
    private val drawerState = MutableStateFlow(DrawerState(initialValue = Closed))

    private val showPlus = MutableStateFlow(true)

    private val savedWorkers = MutableStateFlow(mutableStateListOf<Int>(1,2))

    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                showPlus,
                homeBottomAppBarTabs,
                SelectedHomeBottomAppBarTab,
                drawerState,
                savedWorkers
            ) { showPlus, homeBottomAppBarTabs,SelectedHomeBottomAppBarTab,drawerState,savedWorkers ->
                HomeViewState(
                    showPlus = showPlus,
                    homeAppBarTabs = homeBottomAppBarTabs,
                    selectedHomeBarTab = SelectedHomeBottomAppBarTab,
                    drawerState = drawerState,
                    savedWorkers = savedWorkers
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }



    fun removeFromWatchlist(key:Int){
        savedWorkers.value.remove(key)
        Log.d("main", "removed from watchlist at viewmodel level")
        println(savedWorkers.value.toString())
    }

    fun addToWatchList(key:Int){
        savedWorkers.value.add(key)
        Log.d("main", "added to wathclist at viewmodel level")
        println(savedWorkers.value.toString())
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
    val savedWorkers: MutableList<Int> = mutableListOf(1,2)
)

enum class HomeBottomAppBarTabs{
    Home, Watchlisted, Search
}