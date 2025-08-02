package br.com.flemis.bookishadventure.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

class CustomActivityProvider : Application.ActivityLifecycleCallbacks {
    //private var currentActivity: Activity? = null
    // fun getCurrentActivity(): Activity? = this.currentActivity

    private var currentActivityRef: WeakReference<Activity> = WeakReference(Activity())
    fun getCurrentActivity(): Activity? = currentActivityRef.get()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // sem weak reference currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        // currentActivity =activity
        currentActivityRef = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        //currentActivity = activity
        currentActivityRef = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        /* sem weak reference
        if (currentActivity == activity) {
             currentActivity = null
         }*/
        if (currentActivityRef?.get() == activity) {
            currentActivityRef = WeakReference(null)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        /* sem weak reference
          if (currentActivity == activity) {
               currentActivity = null
           }*/
        if (currentActivityRef?.get() == activity) {
            currentActivityRef = WeakReference(null)
        }
    }
}