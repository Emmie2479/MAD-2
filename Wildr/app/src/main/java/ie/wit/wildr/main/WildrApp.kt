package ie.wit.wildr.main

import android.app.Application
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrStore
import timber.log.Timber

class WildrApp : Application() {

    //lateinit var donationsStore: DonationStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
      //  donationsStore = DonationManager()
        Timber.i("DonationX Application Started")
    }
}