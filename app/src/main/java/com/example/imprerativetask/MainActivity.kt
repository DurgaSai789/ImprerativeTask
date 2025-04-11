package com.example.imprerativetask

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.imprerativetask.navigations.NavGraphComposable
import com.example.imprerativetask.secureStorage.SecureStorage
import com.example.imprerativetask.ui.theme.ImprerativeTaskTheme



class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    private var handleBiometric = true

    //    // Check if token exists
    val storageKey = SecureStorage.getToken()
//
//    val startDestination = if (storageKey != null) {
//        Screens.ListScreen
//    } else {
//        Screens.LoginScreen
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImprerativeTaskTheme {
                val navController = rememberNavController()

                handleBiometric = storageKey == null

                if (handleBiometric){
                    NavGraphComposable(
                        navHostController = navController,
                        loginSuccess = false
                    )
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val biometricResult by promptManager.promptResults.collectAsState(
                            initial = null
                        )
                        val enrollLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartActivityForResult(),
                            onResult = {
                                println("Activity result: $it")
                            }
                        )
                        LaunchedEffect(biometricResult) {
                            if(biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
                                if(Build.VERSION.SDK_INT >= 30) {
                                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                        putExtra(
                                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                        )
                                    }
                                    enrollLauncher.launch(enrollIntent)
                                }
                            }
                        }
                        LaunchedEffect(key1 = true) {
                            promptManager.showBiometricPrompt(
                                title = "Sample prompt",
                                description = "Sample prompt description"
                            )
                        }

                        biometricResult?.let { result ->
                            when(result) {
                                is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                                    result.error
                                }
                                BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                                    "Authentication failed"
                                }
                                BiometricPromptManager.BiometricResult.AuthenticationNotSet  -> {
                                    "Authentication not set"
                                }
                                BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                                    NavGraphComposable(
                                        navHostController = navController,
                                        loginSuccess = true
                                    )
                                }
                                BiometricPromptManager.BiometricResult.FeatureUnavailable  -> {
                                    "Feature unavailable"
                                }
                                BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                                    "Hardware unavailable"
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
