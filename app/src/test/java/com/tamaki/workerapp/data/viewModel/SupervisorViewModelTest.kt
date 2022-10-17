package com.tamaki.workerapp.data.viewModel

import com.google.common.truth.Truth.assertThat
import com.tamaki.workerapp.data.MainDispatcherRule
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.dataStore.FakeDatastore
import com.tamaki.workerapp.data.datastore.DatastoreInterface
import com.tamaki.workerapp.data.repository.FakeRepository
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.SupervisorViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class SupervisorViewModelTest{
    private lateinit var supervisorViewModel: SupervisorViewModel
    private lateinit var fakeRepository: RepositoryInterface
    private lateinit var fakeDataStore: DatastoreInterface


    @get:Rule
    val coroutineRule = MainDispatcherRule()

    @Before
    fun setup(){
        fakeRepository = FakeRepository()
        fakeDataStore = FakeDatastore()
        supervisorViewModel = SupervisorViewModel(fakeRepository, fakeDataStore)
    }


    @Test
    fun `test get supervisor profile`(): Unit = runTest {
        val supervisor = supervisorViewModel.getSupervisorProfile()
        assertThat(supervisor is SupervisorProfile)
    }


    @Test
    fun `test get list of worker accounts for supervisor`(): Unit = runTest{
        val listOfWorkers = supervisorViewModel.getListOfWorkerAccountsForSupervisor()
        assertThat(listOfWorkers is List<WorkerProfile>)
    }
/*
    @Test
    fun `test get supervisor profile`(): Unit = runBlocking{
        val supervisor = supervisorViewModel.addToWatchList()
        assertThat(supervisor is SupervisorProfile)
    }

    @Test
    fun `test get supervisor profile`(): Unit = runBlocking{
        val supervisor = supervisorViewModel.deleteAccount()
        assertThat(supervisor is SupervisorProfile)
    }

    @Test
    fun `test get supervisor profile`(): Unit = runBlocking{
        val supervisor = supervisorViewModel.getWorkerByEmail()
        assertThat(supervisor is SupervisorProfile)
    }

    @Test
    fun `test get supervisor profile`(): Unit = runBlocking{
        val supervisor = supervisorViewModel.getWorkerByEmail()
        assertThat(supervisor is SupervisorProfile)
    }

    @Test
    fun `test get supervisor profile`(): Unit = runBlocking{
        val supervisor = supervisorViewModel.
        assertThat(supervisor is SupervisorProfile)
    }*/

}