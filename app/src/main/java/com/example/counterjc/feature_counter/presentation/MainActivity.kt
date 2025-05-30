package com.example.counterjc.feature_counter.presentation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.example.counterjc.feature_counter.presentation.navigation.MainNavGraph
import com.example.counterjc.ui.theme.CounterJCTheme
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        checkForAppUpdates()
        setContent {
            val mainNavController = rememberNavController()

            CounterJCTheme {
                MainNavGraph(
                    mainNavController = mainNavController,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        updateActivityResultLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )
                }
            }
    }

    private val updateActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Toast.makeText(this, "Update completed successfully", Toast.LENGTH_SHORT).show()
                }

                RESULT_CANCELED -> {
                    Toast.makeText(this, "Update was canceled", Toast.LENGTH_SHORT).show()
                }

                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(
                        this,
                        "Update failed with code: ${result.resultCode}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    private fun checkForAppUpdates() {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                val isUpdateAllowed = info.isImmediateUpdateAllowed

                if (isUpdateAvailable && isUpdateAllowed) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateActivityResultLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )
                }
            }
    }
}
