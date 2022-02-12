package ie.wit.wildr.main

import android.app.Application
import ie.wit.wildr.models.WildrJSONStore
import ie.wit.wildr.models.WildrMemStore
import ie.wit.wildr.models.WildrStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var animals: WildrStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animals = WildrJSONStore(applicationContext)
        i("Wildr started")
    }
}