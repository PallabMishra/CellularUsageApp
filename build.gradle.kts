plugins {
    // Android Gradle Plugin (AGP)
    id("com.android.application") version "8.6.1" apply false
    id("com.android.library") version "8.6.1" apply false

    // Kotlin
    id("org.jetbrains.kotlin.android") version "2.2.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21" apply false
    id("org.jetbrains.kotlin.kapt") version "2.2.21" apply false

    // Hilt Gradle plugin
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
}

