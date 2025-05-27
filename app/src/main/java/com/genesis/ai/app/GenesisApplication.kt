package com.genesis.ai.app

import android.app.Application
import com.genesis.ai.app.service.GenesisAIService
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.auth.FirebaseAuth // Added import
import com.google.firebase.storage.FirebaseStorage // Added import
import com.google.firebase.analytics.FirebaseAnalytics // Added import
import com.google.firebase.firestore.FirebaseFirestore // Added import for Firestore if used elsewhere
import com.google.firebase.messaging.FirebaseMessaging // Added import for Messaging if used elsewhere
import com.google.firebase.database.FirebaseDatabase // Added import for Realtime Database if used elsewhere
import com.google.firebase.FirebaseOptions // Added import if explicit options building is desired, but typically not needed with google-services.json


class GenesisApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase with existing catalog.
        // If you have google-services.json in your app/ directory and the 'google-services' plugin
        // applied in build.gradle.kts, this is usually sufficient and reads config automatically.
        FirebaseApp.initializeApp(this)

        // Initialize Crashlytics
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true

        // Initialize Performance Monitoring
        FirebasePerformance.getInstance().isPerformanceCollectionEnabled = true

        // Initialize Firebase Auth - simply getting the instance is usually enough after FirebaseApp init
        // No explicit 'initialize(this)' call is typically needed here.
        val authInstance = FirebaseAuth.getInstance()
        // You might use authInstance.currentUser to check login state, etc.

        // Initialize Firebase Storage - simply getting the instance is usually enough
        val storageInstance = FirebaseStorage.getInstance()
        // You might use storageInstance.reference to get a reference to storage locations, etc.

        // Initialize Firebase Analytics - getting the instance is enough after FirebaseApp init
        FirebaseAnalytics.getInstance(this).setUserProperty("app_version", BuildConfig.VERSION_NAME)

        // You might also need to get instances for other Firebase services if you use them:
        // val firestoreInstance = FirebaseFirestore.getInstance()
        // val messagingInstance = FirebaseMessaging.getInstance()
        // val databaseInstance = FirebaseDatabase.getInstance()


        // Start the AI service when the application starts
        GenesisAIService.startService(this)
    }
}