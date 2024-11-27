package edu.farmingdale.threadsexample
// Author: Himal Shrestha
// Class: BCS 371 - Mobile Application Development
// Prof: Alrajab
// Week 11 - Threads Example

// Import necessary Android and Jetpack Compose libraries
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.farmingdale.threadsexample.ui.theme.ThreadsExampleTheme

// MainActivity: Entry point for the application
class MainActivity : ComponentActivity() {
    // Override the onCreate method to set up the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge rendering for a modern UI experience
        enableEdgeToEdge()

        // Set the content of the activity using Jetpack Compose
        setContent {
            // Apply the app's theme
            ThreadsExampleTheme {
                // Use a Scaffold for layout structure
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Display the Fibonacci demo UI, passing inner padding as a modifier
                    FibonacciDemoWithCoroutineBackGround(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Greeting function: A simple composable that displays a greeting message
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Display a text element with the greeting message
    Text(
        text = "Hello $name!", // Text to display, using the provided name
        modifier = modifier   // Apply any passed-in modifiers
    )
}

// ToDo 1: Call `FibonacciDemoNoBgThrd` that calculates the Fibonacci number of a given number.
// completed
// ToDo 2: Create a composable function called `FibonacciDemoWithCoroutine` that calculates the
//  Fibonacci number of a given number using a coroutine.
// completed: made and implemented the function
// ToDo 3: Start the application using the CountDownActivity
// completed: the activity now starts with the countdown activity.
// ToDo 4: Make the Text of the timer larger
// completed: timer is now larger approximately 60.sp
// ToDo 5: Show a visual indicator of the timer going down to 0
// completed:
// ToDo 6: Add a button to rest the timer
// completed: reset button has been made and implemented
// ToDo 7: Play a sound when the timer reaches 0
// incomplete: I was not able to get he onTimerFinished to work i kept ketting errors for R and start
// ToDo 8: During the last 10 seconds, make the text red and bold
// completed
