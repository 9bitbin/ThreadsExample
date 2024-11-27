package edu.farmingdale.threadsexample
// Himal Shrestha
// Class: BCS 371 - Mobile Application Development
// Prof Alrajab
// Week 11 - Threads Example

// Import necessary Jetpack Compose and Kotlin libraries
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

// A composable function that calculates Fibonacci without using background threads
@Composable
fun FibonacciDemoNoBgThrd(padding: Modifier) {
    // Mutable state variables to store user input and the resulting Fibonacci number
    var answer by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("40") } // Default input value is 40

    Column {
        // Adds vertical space at the top of the UI
        Spacer(modifier = Modifier.padding(60.dp))
        Row {
            // TextField for user input, accepts numbers only
            TextField(
                value = textInput, // Current input value
                onValueChange = { textInput = it }, // Updates state when input changes
                label = { Text("Number?") }, // Label for the input field
                singleLine = true, // Ensures single-line input
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Numeric keyboard for input
                )
            )
            // Button to calculate the Fibonacci number when clicked
            Button(onClick = {
                val num = textInput.toLongOrNull() ?: 0 // Parse input to a number or default to 0
                val fibNumber = fibonacci(num) // Calculate Fibonacci number
                // Format the result for better readability
                answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
            }) {
                Text("Fibonacci") // Button label
            }
        }
        // Display the result below the input and button
        Text("Result: $answer")
    }
}

// Recursive function to compute the Fibonacci number for a given input
fun fibonacci(n: Long): Long {
    return if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
}

// A composable function that calculates Fibonacci using coroutines for background processing
@Composable
fun FibonacciDemoWithCoroutineBackGround(padding: Modifier) {
    // Mutable state variables for input and result
    var answer by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("40") } // Default input value is 40
    val coroutineScope = rememberCoroutineScope() // Coroutine scope to launch background tasks

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally // Center align horizontally
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically // Center align vertically
        ) {
            // TextField for user input, accepts numbers only
            TextField(
                value = textInput, // Current input value
                onValueChange = { textInput = it }, // Updates state when input changes
                label = { Text("Number?") }, // Label for the input field
                singleLine = true, // Ensures single-line input
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Numeric keyboard for input
                ), modifier = Modifier.weight(1f) // Adjust layout to share space equally
            )
        }

        // Button to calculate Fibonacci using a coroutine
        Button(
            onClick = {
                val num = textInput.toLongOrNull() ?: 0 // Parse input to a number or default to 0
                coroutineScope.launch(Dispatchers.Default) { // Launch a background coroutine
                    val fibNumber = fibonacci(num) // Calculate Fibonacci number in background
                    // Update the result on the main thread
                    answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
                }
            },
            modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally) // Align center
        ) {
            Text("fibonacci with coroutine background") // Button label
        }

        // Display the result below the input and button
        Text("Result: $answer")
    }
}
