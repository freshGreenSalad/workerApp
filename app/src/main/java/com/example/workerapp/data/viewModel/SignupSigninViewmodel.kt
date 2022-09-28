package com.example.workerapp.data.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.workerapp.data.authResult
import com.example.workerapp.data.dataClasses.Licence
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.example.workerapp.data.dataClasses.ProfileInformation
import com.example.workerapp.data.room.YourRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupSigninViewModel @Inject constructor(
    private val repository: YourRepository,
) : ViewModel() {

    //dynamodb function
    suspend fun postAuthProfile() {
        val authresult = repository.postAuthProfile(
            ProfileLoginAuthRequest(
                emailPassword.value.email,
                emailPassword.value.password
            )
        )
        resultChannel.send(authresult)
    }

    private var isSupervisor = false

    suspend fun PostProfileInformation() {
        repository.PostProfileInformation(
            profileInformation = ProfileInformation(
                firstname =firstname.value,
                lastname = lastname.value,
                supervisor = isSupervisor,
                experience = experience.value,
                licences = listOfLicences.value,
                driversLicence = licence.value
            )
        )
    }

    //State variables
    private val userType = MutableStateFlow(EmployeerOrEmployee.values().asList())

    private var listOfLicences = MutableStateFlow(mutableStateListOf("licence"))

    private var experience = MutableStateFlow(mutableStateListOf("formworker 2 years"))

    private var firstname = MutableStateFlow("")

    private var lastname = MutableStateFlow("")

    private val userTypeSelected = MutableStateFlow(EmployeerOrEmployee.Employee)

    private val emailPassword = MutableStateFlow(
        EmailPassword(
            email = "sdfg",
            password = "sdfg"
        )
    )

    private val licence = MutableStateFlow(
        Licence(
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
        )
    )

    //result of profile creation
    private val resultChannel = Channel<authResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    //state init
    private val _state = MutableStateFlow(ProfileCreationPageState())

    val state: StateFlow<ProfileCreationPageState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                firstname,
                lastname,
                userType,
                userTypeSelected,
                listOfLicences,
                experience,
                licence,
                emailPassword,
            ) { firstname, lastname,userType, userTypeSelected, listOfLicences, experience, licence, emailPassword ->
                ProfileCreationPageState(
                    firstname = firstname,
                    lastname = lastname,
                    userType = userType,
                    selectedEmployerOrEmployee = userTypeSelected,
                    listOfLicences = listOfLicences,
                    experience = experience,
                    licence = licence,
                    emailPassword = emailPassword,
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    //extention of combine function as combine maxs out at 5 variables
    private fun <T1, T2, T3, T4, T5, T6,T7,T8, R> combine(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        transform: suspend (T1, T2, T3, T4, T5, T6,T7,T8) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3,::Triple ),
        combine(flow4, flow5, flow6,::Triple),
        combine(flow7, flow8,::Pair),
    ) { t1, t2 ,t3->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
            t3.first,
            t3.second,
        )
    }

    //state updating functions
    fun updateStateEmailPassword(email: String, password: String) {
        emailPassword.value = emailPassword.value.copy(email = email)
        emailPassword.value = emailPassword.value.copy(password = password)
    }

    fun removeFromSpecilisedLicence(key: String) {
        listOfLicences.value.remove(key)
        println(listOfLicences.value.toString())
    }

    fun addSpecilizedLicence(key: String) {
        listOfLicences.value.add(key)
        println(listOfLicences.value.toString())
    }

    fun removeFromExperience(key: String) {
        experience.value.remove(key)
        println(listOfLicences.value.toString())
    }

    fun addExperience(key: String) {
        experience.value.add(key)
        println(listOfLicences.value.toString())
    }

    fun changeUserType(tab: String) {
        when (tab) {
            "Employer" -> {
                userTypeSelected.value = EmployeerOrEmployee.Employer
                isSupervisor = true
            }
            "Employee" -> {
                userTypeSelected.value = EmployeerOrEmployee.Employee
                isSupervisor = false
            }
        }
    }

    fun updatefirstname(name:String){
        firstname.value = name
    }
    fun updatelastname(name:String){
        lastname.value = name
    }

    fun updateLicencefullLicence(licenceValue: String) {
        when (licenceValue) {
            "learners" -> licence.value = licence.value.copy(learners = !licence.value.learners)
            "restricted" -> licence.value =
                licence.value.copy(restricted = !licence.value.restricted)
            "fullLicence" -> licence.value =
                licence.value.copy(fullLicence = !licence.value.fullLicence)
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

//state data classes
enum class EmployeerOrEmployee {
    Employer, Employee
}

data class ProfileCreationPageState constructor(
    val firstname:String = "",
    val lastname: String = "",
    val userType: List<EmployeerOrEmployee> = emptyList(),
    val selectedEmployerOrEmployee: EmployeerOrEmployee = EmployeerOrEmployee.Employer,
    val listOfLicences: MutableList<String> = mutableListOf("Licence"),
    val experience: MutableList<String> = mutableListOf("formworker 2 years"),
    val licence: Licence = Licence(
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
    ),
    val emailPassword: EmailPassword = EmailPassword(email = "email", password = "password"),
)

data class EmailPassword(
    val email: String,
    val password: String
)
