package edu.farmingdale.threadsexample.countdowntimer
//Himal Shrestha
//Class: BCS 371 - Mobile Application Development
//Prof Alrajab
// Week 11 - Threads Example
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import edu.farmingdale.threadsexample.ui.theme.ThreadsExampleTheme


class CountDownActivity : ComponentActivity() {

    // ViewModel instance to manage the timer's state
    private val timerViewModel = TimerViewModel()

    // Launcher for permission request to post notifications (for Android 13 and above)
    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Log message based on whether the permission was granted or not
            val message = if (isGranted) "Permission granted" else "Permission NOT granted"
            Log.i("MainActivity", message)
        }

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Enable edge-to-edge display for the app

        // Set the content of the activity to the timer UI
        setContent {
            ThreadsExampleTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),  // Fill the entire screen
                    color = MaterialTheme.colorScheme.background  // Set background color
                ) {
                    TimerScreen(timerViewModel = timerViewModel)  // Display the TimerScreen
                }
            }
        }

        // For Android 13 and above, check if the app has permission to post notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                // Request the permission if not already granted
                permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Called when the activity is stopped (e.g., when the app goes to the background)
    override fun onStop() {
        super.onStop()

        // If the timer is running, start a background worker to manage the timer state
        if (timerViewModel.isRunning) {
            // For Android 13 and above, check if permission is granted before starting the worker
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Start the worker with the remaining time
                    startWorker(timerViewModel.remainingMillis)
                }
            } else {
                // For older versions, directly start the worker
                startWorker(timerViewModel.remainingMillis)
            }
        }
    }

    // Start a background worker to handle the timer's countdown
    private fun startWorker(millisRemain: Long) {
        // Create a one-time work request to start the timer worker
        val timerWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<TimerWorker>().setInputData(
            `workDataOf`(
                KEY_MILLIS_REMAINING.toString() to millisRemain  // Pass remaining time to the worker
            )
        ).build()

        // Enqueue the work request to the WorkManager to run in the background
        WorkManager.getInstance(applicationContext).enqueue(timerWorkRequest)
    }
}
