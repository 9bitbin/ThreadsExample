# Countdown Timer with Alarm Sound ⏲️🔔

## Overview
This project implements a countdown timer with an alarm sound feature, built using Android Jetpack Compose and Kotlin. The app allows users to set a countdown timer based on hours, minutes, and seconds, and it plays an alarm sound when the timer reaches zero.

### Features:
- ⏰ **Timer Duration Selection**: Users can select the timer duration (hours, minutes, seconds).
- 🔄 **Countdown**: The countdown timer shows the remaining time and updates every second.
- ▶️ **Start, Pause & Reset**: The timer can be started, paused, and reset.
- 🔊 **Alarm Sound**: When the timer finishes, an alarm sound plays to notify the user.
- 🟥 **Last 10 Seconds Highlight**: During the last 10 seconds, the timer text becomes red and bold for emphasis.
- 🌟 **Modern UI**: Edge-to-edge rendering is used for a sleek, modern UI experience.

## Completed TODOs ✅
1. **Call `FibonacciDemoNoBgThrd` that calculates the Fibonacci number of a given number.**
   - **Status**: ✅ Completed
   - **Description**: Implemented a function that calculates the Fibonacci number using a background thread.

2. **Create a composable function called `FibonacciDemoWithCoroutine` that calculates the Fibonacci number of a given number using a coroutine.**
   - **Status**: ✅ Completed
   - **Description**: Created a composable function that calculates Fibonacci numbers asynchronously using Kotlin coroutines.

3. **Start the application using the `CountDownActivity`.**
   - **Status**: ✅ Completed
   - **Description**: The application now starts with the countdown timer activity.

4. **Make the text of the timer larger.**
   - **Status**: ✅ Completed
   - **Description**: Increased the text size of the timer to approximately 60sp for better visibility.

5. **Show a visual indicator of the timer going down to 0.**
   - **Status**: ✅ Completed
   - **Description**: The UI now dynamically updates to show the countdown and remaining time visually.

6. **Add a button to reset the timer.**
   - **Status**: ✅ Completed
   - **Description**: Added a reset button to allow the user to reset the timer to its initial state.

7. **Play a sound when the timer reaches 0.**
   - **Status**: ❌ incomplete
   - **Description**: attempted to integrate sound playback using `MediaPlayer` to play an alarm sound when the timer reaches zero. The sound is played from a `res/raw` folder resource (`alarmsound.wav`), was not successfull //commented out

8. **During the last 10 seconds, make the text red and bold.**
   - **Status**: ✅ Completed
   - **Description**: The timer text changes to red and bold during the last 10 seconds of the countdown to provide a visual cue to the user.

## Requirements 📦

- Android Studio 💻
- Kotlin 1.7+ (or compatible) 📝
- Android SDK (API level 21 or higher) 📱
