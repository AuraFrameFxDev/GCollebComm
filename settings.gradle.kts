pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
    
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library" -> 
                    useModule("com.android.tools.build:gradle:8.2.2")
                "org.jetbrains.kotlin.android" -> 
                    useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
                "com.google.gms.google-services" -> 
                    useModule("com.google.gms:google-services:4.4.0")
                "com.google.firebase.crashlytics" -> 
                    useModule("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
                "androidx.navigation.safeargs.kotlin" ->
                    useModule("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(org.gradle.api.initialization.resolve.RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
    
    versionCatalogs {
        create("libs") {
            version("retrofit", "2.9.0")
            version("okhttp", "4.12.0")
            version("room", "2.6.1")
            
            library("retrofit-core", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("retrofit-converter-gson", "com.squareup.retrofit2", "converter-gson").versionRef("retrofit")
            library("okhttp", "com.squareup.okhttp3", "okhttp").versionRef("okhttp")
            library("okhttp-logging", "com.squareup.okhttp3", "logging-interceptor").versionRef("okhttp")
            library("room-runtime", "androidx.room", "room-runtime").versionRef("room")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("room")
        }
    }
}

rootProject.name = "GenesisAndroidApp"

// Include the app module
include(":app")