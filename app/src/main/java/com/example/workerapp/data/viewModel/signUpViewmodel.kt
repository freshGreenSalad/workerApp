package com.example.workerapp.data.viewModel

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workerapp.data.authResult
import com.example.workerapp.data.models.Licence
import com.example.workerapp.data.room.YourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class signUpViewModel @Inject constructor(
    private val repository: YourRepository,
) : ViewModel() {

    private val userType = MutableStateFlow(EmployeerOrEmployee.values().asList())

    private var listOfLicences =MutableStateFlow(  mutableStateListOf("licence") )

    private var experience =MutableStateFlow(  mutableStateListOf("formworker 2 years") )

    private val userTypeSelected = MutableStateFlow(EmployeerOrEmployee.Employee)

    private val resultChannel = Channel<authResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    private val licence = MutableStateFlow(Licence(
        fullLicence = false,
        learners = false,
        restricted = false,
        wheels = false,
        tracks = false,
        rollers = false,
        forks = false,
        hazardus = false,
        class2 = false,
        class3 = false,
        class4 = false,
        class5 = false
    ))

    private val _state = MutableStateFlow(ProfileCreationPageState())

    val state: StateFlow<ProfileCreationPageState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                userType,
                userTypeSelected,
                listOfLicences,
                experience,
                licence
            ) {userType, userTypeSelected,listOfLicences,experience,licence  ->
                ProfileCreationPageState(
                    userType = userType,
                    selectedEmployeerOrEmployee = userTypeSelected,
                    listOfLicences,
                    experience,
                    licence
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }
    fun removeFromSpecilisedLicence(key: String) {
        listOfLicences.value.remove(key)
        Log.d("main", "removed from watchlist at viewmodel level")
        println(listOfLicences.value.toString())
    }

    fun addSpecilizedLicence(key: String) {
        listOfLicences.value.add(key)
        Log.d("main", "added to wathclist at viewmodel level")
        println(listOfLicences.value.toString())
    }

    fun removeFromExperience(key: String) {
        experience.value.remove(key)
        Log.d("main", "removed from watchlist at viewmodel level")
        println(listOfLicences.value.toString())
    }

    fun addExperience(key: String) {
        experience.value.add(key)
        Log.d("main", "added to wathclist at viewmodel level")
        println(listOfLicences.value.toString())
    }

    fun changeUserType(tab: String) {
        when(tab) {
            "Employer" -> userTypeSelected.value = EmployeerOrEmployee.Employer
            "Employee" -> userTypeSelected.value = EmployeerOrEmployee.Employee
        }
    }

    fun UpdateLicencefullLicence(licenceValue:String){
        when(licenceValue) {
            "learners" -> licence.value = licence.value.copy(learners = !licence.value.learners)
            "restricted" -> licence.value = licence.value.copy(restricted = !licence.value.restricted)
            "fullLicence" -> licence.value = licence.value.copy(fullLicence = !licence.value.fullLicence)
            "wheels" -> licence.value = licence.value.copy(wheels = !licence.value.wheels)
            "tracks" -> licence.value = licence.value.copy(tracks = !licence.value.tracks)
            "rollers" -> licence.value = licence.value.copy(rollers = !licence.value.rollers)
            "forks" -> licence.value = licence.value.copy(forks = !licence.value.forks)
            "hazardus" -> licence.value = licence.value.copy(hazardus = !licence.value.hazardus)
            "class2" -> licence.value = licence.value.copy(class2 = !licence.value.class2)
            "class3" -> licence.value = licence.value.copy(class3 = !licence.value.class3)
            "class4" -> licence.value = licence.value.copy(class4 = !licence.value.class4)
            "class5" -> licence.value = licence.value.copy(class5 = !licence.value.class5)
        }
    }
}

enum class EmployeerOrEmployee{
    Employer, Employee
}


data class ProfileCreationPageState constructor(
    val userType:List<EmployeerOrEmployee> = emptyList(),
    val selectedEmployeerOrEmployee: EmployeerOrEmployee = EmployeerOrEmployee.Employer,
    val listOfLicences: MutableList<String> = mutableListOf("Licence"),
    val experience: MutableList<String> = mutableListOf("formworker 2 years"),
    val licence: Licence = Licence(false,false,false, false, false, false, false, false,false,false, false, false)
)
