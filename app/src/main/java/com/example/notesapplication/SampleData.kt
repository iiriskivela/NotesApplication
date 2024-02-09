package com.example.notesapplication

/**
 * SampleData for Jetpack Compose Tutorial
 */
object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Lexi",
            "Test...Test...Test...",
            null
        ),
        Message(
            "Lexii",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim(),
            null
        ),
        Message(
            "Lexi",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim(),
            null
        ),
        Message(
            "Lexii",
            "Searching for alternatives to XML layouts...",
            null
        ),
        Message(
            "Lexi",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim(),
            null
        ),
        Message(
            "Lexii",
            "It's available from API 21+ :)",
            null
        ),
        Message(
            "Lexii",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?",
            null
        ),
        Message(
            "Lexi",
            "Android Studio next version's name is Arctic Fox",
            null
        ),
        Message(
            "Lexii",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^",
            null
        ),
        Message(
            "Lexii",
            "I didn't know you can now run the emulator directly from Android Studio",
            null
        ),
        Message(
            "Lexi",
            "Compose Previews are great to check quickly how a composable layout looks like",
            null
        ),
        Message(
            "Lexii",
            "Previews are also interactive after enabling the experimental setting",
            null
        ),
        Message(
            "Lexi",
            "Have you tried writing build.gradle with KTS?",
            null
        ),
    )
}
