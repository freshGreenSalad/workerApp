package com.example.workerapp.data.viewModel

import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.workerapp.data.authResult
import com.example.workerapp.data.dataClasses.Licence
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.example.workerapp.data.room.YourRepository
import com.google.maps.android.compose.MapProperties
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
        val authResult = repository.postAuthProfile(
            ProfileLoginAuthRequestWithIsSupervisor(
                emailPassword.value.email,
                emailPassword.value.password,
                emailPassword.value.isSupervisor
            )
        )
        resultChannel.send(authResult)
    }

    //result of profile creation
    private val resultChannel = Channel<authResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    //______________________________________________________________________________________________________________________________________________
    private val _stateMap = MutableStateFlow(MapState())

    val stateMap: StateFlow<MapState>
        get() = _stateMap

    private var map = MutableStateFlow(MapDataClass())
    private var holder = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                map,
                holder,
            ) { map, holder ->
                MapState(
                    map,
                    holder,
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _stateMap.value = it
            }
        }
    }
    //______________________________________________________________________________________________________________________________________________

    private val _stateName = MutableStateFlow(NameState())

    val stateName: StateFlow<NameState>
        get() = _stateName

    private var firstname = MutableStateFlow("")

    private var lastname = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                firstname,
                lastname,
            ) { firstname, lastname ->
                NameState(
                    firstname,
                    lastname,
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _stateName.value = it
            }
        }
    }

    fun updateFirstname(name: String) {
        firstname.value = name
    }

    fun updateLastname(name: String) {
        lastname.value = name
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateTickets = MutableStateFlow(TicketState())

    val stateTickets: StateFlow<TicketState>
        get() = _stateTickets

    private var ticketTypeState = MutableStateFlow(TicketType.DriversLicence)

    private var licenceType = MutableStateFlow(TypeOfLicence.Empty)

    private var licenceMap = MutableStateFlow(
        mutableStateMapOf(
            Pair("forks", false),
            Pair("wheels", false),
            Pair("rollers", false),
            Pair("dangerousGoods", false),
            Pair("tracks", false)
        )
    )

    private var highestClassState = MutableStateFlow(HighestClass.Class1)

    init {
        viewModelScope.launch {
            combine(
                ticketTypeState,
                licenceType,
                licenceMap,
                highestClassState

            ) { ticketType, licenceType, licenceMap, highestClass ->
                TicketState(
                    ticketType = ticketType,
                    licenceType = licenceType,
                    licenceMap = licenceMap,
                    highestClass = highestClass
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _stateTickets.value = it
            }
        }
    }

    fun changeHighestClass(highestClass: HighestClass){
        highestClassState.value = highestClass
    }

    fun changeTicketType(ticketType: TicketType) {
        ticketTypeState.value = ticketType
    }

    fun changeLicenceType(chosenLicenceType: TypeOfLicence) {
        licenceType.value = chosenLicenceType
    }

    fun updateLicenceMap(licenceVar: String) {
        licenceMap.value[licenceVar] = !licenceMap.value[licenceVar]!!
        println(licenceMap.value[licenceVar])
    }

    //______________________________________________________________________________________________________________________________________________
    private val _stateCamera = MutableStateFlow(CameraState())

    val stateCamera: StateFlow<CameraState>
        get() = _stateCamera

    private val shouldShowCamera = MutableStateFlow(true)

    private val shouldShowPhoto = MutableStateFlow(false)

    private val photoUri = MutableStateFlow(Uri.EMPTY)

    init {
        viewModelScope.launch {
            combine(
                shouldShowCamera,
                shouldShowPhoto,
                photoUri
            ) { shouldShowCamera, shouldShowPhoto, photoUri ->
                CameraState(
                    shouldShowCamera,
                    shouldShowPhoto,
                    photoUri
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _stateCamera.value = it
            }
        }
    }

    fun shouldshowcam(bool: Boolean) {
        shouldShowCamera.value = bool
    }

    fun shouldshowPho(bool: Boolean) {
        shouldShowPhoto.value = bool
    }

    fun updatePhotoURI(uri: Uri) {
        photoUri.value = uri
    }

    //______________________________________________________________________________________________________________________________________________
    private val _state = MutableStateFlow(ProfileCreationPageState())

    val state: StateFlow<ProfileCreationPageState>
        get() = _state

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
    private val emailPassword = MutableStateFlow(
        EmailPassword(
            email = "sdfg",
            password = "sdfg",
            isSupervisor = true
        )
    )

    private val userTypeSelected = MutableStateFlow(EmployeerOrEmployee.Employee)

    private val workerSignUpPoint = MutableStateFlow(WorkerSignUpPoint.BasicInformation)

    private var listOfLicences = MutableStateFlow(mutableStateListOf("licence"))

    private var experience = MutableStateFlow(mutableStateListOf("formworker 2 years"))

    private var isSupervisor = false

    init {
        viewModelScope.launch {
            combine(
                userTypeSelected,
                listOfLicences,
                experience,
                emailPassword,
                workerSignUpPoint
            ) { userTypeSelected, listOfLicences, experience, emailPassword, workerSignUpPoint ->
                ProfileCreationPageState(
                    selectedEmployerOrEmployee = userTypeSelected,
                    workerSignUpPoint = workerSignUpPoint,
                    listOfLicences = listOfLicences,
                    experience = experience,
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
    //______________________________________________________________________________________________________________________________________________

    //extention of combine function as combine maxs out at 5 variables
    private fun <T1, T2, T3, T4, T5, T6, R> combine(
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

    private fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> combine(
        flow: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        flow5: Flow<T5>,
        flow6: Flow<T6>,
        flow7: Flow<T7>,
        flow8: Flow<T8>,
        flow9: Flow<T9>,
        transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
    ): Flow<R> = combine(
        combine(flow, flow2, flow3, ::Triple),
        combine(flow4, flow5, flow6, ::Triple),
        combine(flow7, flow8, flow9, ::Triple),
    ) { t1, t2, t3 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third,
            t3.first,
            t3.second,
            t3.third
        )
    }

    //state updating functions
    //camera state updating functions


    //__________________________
    fun updateStateEmailPassword(email: String, password: String, isSupervisor: Boolean) {
        emailPassword.value = emailPassword.value.copy(
            email = email,
            password = password,
            isSupervisor = isSupervisor
        )
    }

    fun nextScreen() {
        when (workerSignUpPoint.value) {
            WorkerSignUpPoint.BasicInformation -> workerSignUpPoint.value =
                WorkerSignUpPoint.Tickets
            WorkerSignUpPoint.Tickets -> workerSignUpPoint.value = WorkerSignUpPoint.Experience
            WorkerSignUpPoint.Experience -> workerSignUpPoint.value =
                WorkerSignUpPoint.BasicInformation
        }
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
}

//state data classes
enum class EmployeerOrEmployee {
    Employer, Employee
}

enum class WorkerSignUpPoint {
    BasicInformation, Experience, Tickets
}

enum class TypeOfLicence {
    Learners, Restricted, Full, Empty
}

enum class HighestClass {
    Class1, Class2, Class3, Class4, Class5
}

enum class TicketType {
    Crane, DangerousSpaces, DriversLicence, Lifts, Empty
}

data class CameraState constructor(
    val shouldShowCamera: Boolean = true,
    val shouldShowPhoto: Boolean = false,
    val photoUri: Uri = Uri.EMPTY
)

data class NameState constructor(
    val firstname: String = "",
    val lastname: String = "",
)

data class TicketState constructor(
    val ticketType: TicketType = TicketType.DriversLicence,
    val licenceType: TypeOfLicence = TypeOfLicence.Empty,
    val licenceMap: MutableMap<String, Boolean> = mutableMapOf(Pair("", true)),
    val highestClass: HighestClass = HighestClass.Class1
)

data class ProfileCreationPageState constructor(
    //val userType: List<EmployeerOrEmployee> = emptyList(),
    val selectedEmployerOrEmployee: EmployeerOrEmployee = EmployeerOrEmployee.Employer,
    val listOfLicences: MutableList<String> = mutableListOf("Licence"),
    val workerSignUpPoint: WorkerSignUpPoint = WorkerSignUpPoint.BasicInformation,
    val experience: MutableList<String> = mutableListOf("formworker 2 years"),

    val emailPassword: EmailPassword = EmailPassword(
        email = "email",
        password = "password",
        isSupervisor = true
    ),
)

data class EmailPassword(
    val email: String,
    val password: String,
    val isSupervisor: Boolean
)

data class MapDataClass(
    val properties: MapProperties = MapProperties(),
)

data class MapState(
    val map: MapDataClass = MapDataClass(),
    val holder: String = ""
)
