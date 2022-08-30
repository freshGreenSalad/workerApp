package com.example.workerapp.ui.homeUi


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
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.workerapp.data.room.*
import com.example.workerapp.data.cashing.WorkerCasheMap
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest

private val Context.dataStore by preferencesDataStore("user_preferences")
@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: YourRepository,
) : ViewModel() {

    suspend fun getworker(key: Int): String {
        val index = key + 1
        val cashedcharacter = WorkerCasheMap.workerCashMap[index]
        return if (cashedcharacter != null) {
            cashedcharacter
        } else {
            val response = repository.getworkerstring(index)
            WorkerCasheMap.workerCashMap[index] = response
            response
        }
    }
    //val context = LocalContext.current


    suspend fun save(key: String, value: String, context: Context) {
        val dataStore = context.dataStore
        val dataStoreKey = stringPreferencesKey( name = key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
    suspend fun read(key: String, context: Context): String? {
        val dataStore = context.dataStore
        val dataStoreKey = stringPreferencesKey( name = key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun authenticate(jwt:String) = repository.authenticate(jwt)

    suspend fun login(authRequest: ProfileLoginAuthRequest): String = repository.login(authRequest)

    suspend fun postprofile(profile: Profile) = repository.postprofile(profile)

    suspend fun getProfile():Profile = repository.getProfile()

    suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequest) = repository.postAuthProfile(profileLoginAuthRequest)

    private val homeBottomAppBarTabs = MutableStateFlow(HomeBottomAppBarTabs.values().asList())

    private val selectedHomeBottomAppBarTab = MutableStateFlow(HomeBottomAppBarTabs.Home)

    suspend fun upsert(profile: Profile) = repository.upsert(profile)

    @OptIn(ExperimentalMaterial3Api::class)
    private val drawerState = MutableStateFlow(DrawerState(initialValue = Closed))

    private val savedWorkers = MutableStateFlow(mutableStateListOf(1, 2))

    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                homeBottomAppBarTabs,
                selectedHomeBottomAppBarTab,
                drawerState,
                savedWorkers
            ) { homeBottomAppBarTabs, SelectedHomeBottomAppBarTab, drawerState, savedWorkers ->
                HomeViewState(
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

    fun removeFromWatchlist(key: Int) {
        savedWorkers.value.remove(key)
        Log.d("main", "removed from watchlist at viewmodel level")
        println(savedWorkers.value.toString())
    }

    fun addToWatchList(key: Int) {
        savedWorkers.value.add(key)
        Log.d("main", "added to wathclist at viewmodel level")
        println(savedWorkers.value.toString())
    }

    fun onClickHomeBottomAppTab(tab: HomeBottomAppBarTabs) {
        selectedHomeBottomAppBarTab.value = tab
    }

}

private operator fun String.set(dataStoreKey: Preferences.Key<String>, value: String) {

}

data class HomeViewState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val homeAppBarTabs: List<HomeBottomAppBarTabs> = emptyList(),
    val selectedHomeBarTab: HomeBottomAppBarTabs = HomeBottomAppBarTabs.Home,
    val drawerState: DrawerState = DrawerState(initialValue = Closed),
    val savedWorkers: MutableList<Int> = mutableListOf(1, 2)
)

enum class HomeBottomAppBarTabs {
    Home, Watchlisted, Search
}