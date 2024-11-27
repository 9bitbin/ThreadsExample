package edu.farmingdale.threadsexample.countdowntimer
//Himal Shrestha
//Class: BCS 371 - Mobile Application Development
//Prof Alrajab
// Week 11 - Threads Example
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// ViewModel class to manage the state and logic of a countdown timer
class TimerViewModel : ViewModel() {
    // Coroutine job for managing the timer lifecycle
    private var timerJob: Job? = null

    // Callback function to execute when the timer finishes
    var onTimerFinished: (() -> Unit)? = null

    // State variables to store the selected time components
    var selectedHour by mutableIntStateOf(0)
        private set
    var selectedMinute by mutableIntStateOf(0)
        private set
    var selectedSecond by mutableIntStateOf(0)
        private set

    // Total time in milliseconds calculated from selected time
    var totalMillis by mutableLongStateOf(0L)
        private set

    // Remaining time in milliseconds during the countdown
    var remainingMillis by mutableLongStateOf(0L)
        private set

    // Boolean state to indicate whether the timer is running
    var isRunning by mutableStateOf(false)
        private set

    // Function to set the selected time from hour, minute, and second inputs
    fun selectTime(hour: Int, min: Int, sec: Int) {
        selectedHour = hour
        selectedMinute = min
        selectedSecond = sec
    }

    // Starts the timer countdown
    fun startTimer() {
        // Calculate total milliseconds from the selected time
        totalMillis = (selectedHour * 60 * 60 + selectedMinute * 60 + selectedSecond) * 1000L

        // Start the timer if the total time is greater than zero
        if (totalMillis > 0) {
            isRunning = true
            remainingMillis = totalMillis

            // Launch a coroutine to decrement the remaining time
            timerJob = viewModelScope.launch {
                while (remainingMillis > 0) {
                    delay(1000) // Wait for 1 second
                    remainingMillis -= 1000 // Reduce remaining time by 1 second
                }

                // Timer finished, update state and invoke callback
                isRunning = false
                onTimerFinished?.invoke()
            }
        }
    }

    // Cancels the timer if it's running
    fun cancelTimer() {
        if (isRunning) {
            timerJob?.cancel() // Cancel the running coroutine
            isRunning = false
            remainingMillis = 0 // Reset remaining time
        }
    }

    // Resets the timer to its initial state
    fun resetTimer() {
        timerJob?.cancel() // Cancel the running coroutine if any
        isRunning = false
        remainingMillis = 0
        selectedHour = 0
        selectedMinute = 0
        selectedSecond = 0
    }


    // Ensures any active timer coroutine is canceled when ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
