package edu.farmingdale.threadsexample.countdowntimer
//Himal Shrestha
//Class: BCS 371 - Mobile Application Development
//Prof Alrajab
// Week 11 - Threads Example
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.farmingdale.threadsexample.R
import kotlinx.coroutines.delay

// Constants for notification channel ID, notification ID, and key for remaining time
const val CHANNEL_ID_TIMER = "channel_timer"
const val NOTIFICATION_ID = 0
const val KEY_MILLIS_REMAINING = 2000

// TimerWorker handles countdown timer logic and notifications
class TimerWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    // Reference to the system's notification manager
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // This is the main function executed by the worker
    override suspend fun doWork(): Result {
        // Retrieve the remaining milliseconds passed as input
        var remainingMillis = inputData.getLong(KEY_MILLIS_REMAINING.toString(), 0)

        // If no remaining time is provided, stop the worker
        if (remainingMillis == 0L) {
            return Result.failure()
        }

        // Create a notification channel to display timer updates
        createTimerNotificationChannel()

        // Loop until the remaining time reaches zero
        while (remainingMillis > 0) {
            // Post a notification showing the remaining time
            postTimerNotification(timerText(remainingMillis))
            delay(1000) // Wait for 1 second
            remainingMillis -= 1000 // Decrease the remaining time by 1 second
        }

        // Post a final notification indicating the timer is finished
        postTimerNotification("Timer is finished!")

        // Indicate successful completion of the work
        return Result.success()
    }

    // Creates a notification channel for the timer notifications (required for Android O+)
    private fun createTimerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_TIMER,
                "Timer Channel",
                NotificationManager.IMPORTANCE_LOW // Low importance to avoid sound/vibration
            )
            channel.description = "Displays how much time is left"

            // Register the notification channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Posts a notification with the given text
    private fun postTimerNotification(text: String) {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_TIMER)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Notification icon
            .setContentTitle(applicationContext.getString(R.string.app_name)) // App name as title
            .setContentText(text) // Dynamic notification text
            .setPriority(NotificationCompat.PRIORITY_LOW) // Low priority notification
            .build()

        // Check if notifications are enabled before posting
        if (notificationManager.areNotificationsEnabled()) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

        // Log the notification text for debugging purposes
        Log.d("TimerWorker", text)
    }
}
