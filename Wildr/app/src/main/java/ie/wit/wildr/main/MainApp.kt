package ie.wit.wildr.main

import android.app.Application
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrStore
import timber.log.Timber

class MainApp : Application() {

    //lateinit var animalsStore: WildrStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //  animalsStore = WildrManager()
        Timber.i("Wildr Application Started")
    }
}