package edu.farmingdale.threadsexample.countdowntimer
//Himal Shrestha
//Class: BCS 371 - Mobile Application Development
//Prof Alrajab
// Week 11 - Threads Example
import android.content.Context
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.DecimalFormat
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TimerScreen(

    modifier: Modifier = Modifier,
    timerViewModel: TimerViewModel = viewModel(),
) {
    // Check if the timer is in its last 10 seconds
    val isLastTenSeconds = timerViewModel.remainingMillis <= 10_000 && timerViewModel.isRunning

    // Main Column layout for timer and controls
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // Box that displays the remaining time as a formatted text
        Box(
            modifier = modifier
                .padding(20.dp)
                .size(240.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = timerText(timerViewModel.remainingMillis),
                fontSize = 58.sp,
                color = if (isLastTenSeconds) Color.Red else Color.Black, // Red color for last 10 seconds
                fontWeight = if (isLastTenSeconds) FontWeight.Bold else FontWeight.Normal // Bold text for last 10 seconds
            )
        }

        // TimePicker composable to allow the user to set the time
        TimePicker(
            hour = timerViewModel.selectedHour,
            min = timerViewModel.selectedMinute,
            sec = timerViewModel.selectedSecond,
            onTimePick = timerViewModel::selectTime
        )

        // Row of buttons for controlling the timer (Start, Cancel, Reset)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 50.dp) // Apply consistent top padding to the Row
        ) {
            if (timerViewModel.isRunning) {
                Button(
                    onClick = timerViewModel::cancelTimer
                ) {
                    Text("Cancel")
                }
            } else {
                Button(
                    enabled = timerViewModel.selectedHour + timerViewModel.selectedMinute + timerViewModel.selectedSecond > 0,
                    onClick = timerViewModel::startTimer
                ) {
                    Text("Start")
                }
            }

            Button(
                onClick = timerViewModel::resetTimer
            ) {
                Text("Reset")
            }
        }

    }
}


// Function to format the timer text (HH:MM:SS format)
fun timerText(timeInMillis: Long): String {
    val duration: Duration = timeInMillis.milliseconds
    return String.format(
        Locale.getDefault(),
        "%02d:%02d:%02d",
        duration.inWholeHours,
        duration.inWholeMinutes % 60,
        duration.inWholeSeconds % 60
    )
}

@Composable
fun TimePicker(
    hour: Int = 0,
    min: Int = 0,
    sec: Int = 0,
    onTimePick: (Int, Int, Int) -> Unit = { _: Int, _: Int, _: Int -> },
) {
    // Remember the values for the hour, minute, and second
    var hourVal by remember { mutableIntStateOf(hour) }
    var minVal by remember { mutableIntStateOf(min) }
    var secVal by remember { mutableIntStateOf(sec) }

    // Row to display hour, minute, and second pickers
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
    ) {
        // Hour picker column
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hours")
            NumberPickerWrapper(initVal = hourVal, maxVal = 99, // Max value for hours
                onNumPick = {
                    hourVal = it
                    onTimePick(hourVal, minVal, secVal) // Update selected time when hour changes
                })
        }

        // Minute picker column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Text("Minutes")
            NumberPickerWrapper(initVal = minVal, onNumPick = {
                minVal = it
                onTimePick(hourVal, minVal, secVal) // Update selected time when minute changes
            })
        }

        // Second picker column
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Seconds")
            NumberPickerWrapper(initVal = secVal, onNumPick = {
                secVal = it
                onTimePick(hourVal, minVal, secVal) // Update selected time when second changes
            })
        }
    }
}

@Composable
fun NumberPickerWrapper(
    initVal: Int = 0,
    minVal: Int = 0,
    maxVal: Int = 59,
    onNumPick: (Int) -> Unit = {},
) {
    // Number picker formatting to display two digits
    val numFormat = NumberPicker.Formatter { i: Int ->
        DecimalFormat("00").format(i)
    }

    // Android view wrapper for the NumberPicker widget
    AndroidView(factory = { context ->
        NumberPicker(context).apply {
            setOnValueChangedListener { numberPicker, oldVal, newVal -> onNumPick(newVal) }
            minValue = minVal
            maxValue = maxVal
            value = initVal
            setFormatter(numFormat) // Format the number to two digits
        }
    })
}
