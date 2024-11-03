plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" // замените на текущую версию Kotlin
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.8.2"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}