// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
    }
    dependencies {
        // Keep the version aligned with the one in plugins block
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        
        // Google Services and Firebase
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        
        // Navigation Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        
        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        
        // Secrets Gradle Plugin for API keys
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Core plugins
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    
    // Firebase
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    
    // Kotlin plugins
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    
    // Hilt
    id("com.google.dagger.hilt.android") version "2.48" apply false
    
    // Navigation Safe Args
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    
    // Secrets Gradle Plugin
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// Root build.gradle.kts
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
    // or just delete(layout.buildDirectory) if this task is defined directly in root project
}