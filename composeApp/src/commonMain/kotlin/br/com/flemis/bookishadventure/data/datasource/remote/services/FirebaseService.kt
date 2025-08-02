package br.com.flemis.bookishadventure.data.datasource.remote.services
/*
package br.com.fernandosini.bookishadventure.core.services
import br.com.fernandosini.bookishadventure.getPlatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.analytics.Event
import dev.gitlive.firebase.analytics.FirebaseAnalyticsEvents
import dev.gitlive.firebase.analytics.FirebaseAnalyticsParam
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.initialize

interface FirebaseService {


    suspend fun initFirebase() {
        val androidOptions = FirebaseOptions(
            applicationId = "1:111866851038:android:650580b7d7d1346d289c4f",
            projectId = "bookish-adventure",
            apiKey = "AIzaSyDPnXetUZ5LfUSZ8rcN6KCLk2KXKEb_2OY",
            storageBucket = "bookish-adventure.firebasestorage.app",
            gcmSenderId = "111866851038",
            gaTrackingId = "10287427102"
        )
        val iosOptions = FirebaseOptions(
            applicationId = "1:111866851038:ios:fc30e1ebbd404a67289c4f",
            gaTrackingId = "10287510875",
            gcmSenderId = "111866851038",
            projectId = "bookish-adventure",
            storageBucket = "bookish-adventure.firebasestorage.app",
            apiKey = "AIzaSyCpC_y3CyuHd3yrqCf5YSzJThQW8V-PfwU"

        )

        Firebase.initialize(
            this, options = when (getPlatform().name.lowercase()) {
                "ios" -> iosOptions
                "android" -> androidOptions
                else -> throw IllegalArgumentException("Unsupported current platform")
            }
        )

    }

    suspend fun logEvent(eventName: String, params: Map<String, Any>?) {
        Firebase.analytics.logEvent(eventName, params)
    }

}
*/
