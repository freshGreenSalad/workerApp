package com.tamaki.workerapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.viewModel.WorkerViewModel
import com.tamaki.workerapp.ui.theme.WorkerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.SupervisorViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.tamaki.workerapp.data.viewModel.SigninViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
        } else {
            Log.i("kilo", "Permission denied")
        }
    }
    val context = this@MainActivity
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkerAppTheme {
                val animation = NestedNavGraphDefaultAnimations(
                    enterTransition = { slideInHorizontally(animationSpec = tween(500)) },
                    exitTransition = { slideOutHorizontally(animationSpec = tween(500)) }
                )
                DestinationsNavHost(
                    engine = rememberAnimatedNavHostEngine(
                        navHostContentAlignment = Alignment.TopCenter,
                        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                        defaultAnimationsForNestedNavGraph = mapOf(
                            NavGraphs.root to animation,
                            NavGraphs.worker to animation,
                            NavGraphs.homeView to animation,
                            NavGraphs.profileCreation to animation,
                        )
                    ),
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(NavGraphs.homeView) {
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry(NavGraphs.root.route)
                            }
                            hiltViewModel<SupervisorViewModel>(parentEntry)
                        }
                        dependency(NavGraphs.profileCreation) {
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry(NavGraphs.root.route)
                            }
                            hiltViewModel<SignupViewModel>(parentEntry)
                        }
                        dependency(NavGraphs.signin) {
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry(NavGraphs.root.route)
                            }
                            hiltViewModel<SigninViewModel>(parentEntry)
                        }
                        dependency(NavGraphs.worker) {
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry(NavGraphs.root.route)
                            }
                            hiltViewModel<WorkerViewModel>(parentEntry)
                        }
                    }
                )
            }
        }
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }


    /*override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }*/
}

