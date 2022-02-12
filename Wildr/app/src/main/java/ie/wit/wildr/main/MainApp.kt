package ie.wit.wildr.main

import android.app.Application
import ie.wit.wildr.models.WildrModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val animals = ArrayList<WildrModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Wildr started")
    }
}