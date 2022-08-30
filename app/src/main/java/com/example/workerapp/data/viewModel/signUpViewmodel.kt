package com.example.workerapp.data.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workerapp.data.room.YourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class signUpViewModel @Inject constructor(
    private val repository: YourRepository,
) : ViewModel() {

    val userType = MutableStateFlow(EmployeerOrEmployee.values().asList())

    val userTypeSelected = MutableStateFlow(EmployeerOrEmployee.employer)

    private val _state = MutableStateFlow(ProfileCreationPageState())

    val state: StateFlow<ProfileCreationPageState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                userType,
                userTypeSelected
            ) {userType, userTypeSelected  ->
                ProfileCreationPageState(
                    userType = userType,
                    selectedEmployeerOrEmployee = userTypeSelected
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    fun changeUserType(tab: EmployeerOrEmployee) {
        userTypeSelected.value = tab
    }
}

enum class EmployeerOrEmployee{
    employer, employee
}

data class ProfileCreationPageState constructor(
    val userType:List<EmployeerOrEmployee> = emptyList(),
    val selectedEmployeerOrEmployee: EmployeerOrEmployee = EmployeerOrEmployee.employer
)
