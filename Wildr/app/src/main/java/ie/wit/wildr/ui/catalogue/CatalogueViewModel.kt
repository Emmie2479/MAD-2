package ie.wit.wildr.ui.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrModel
import timber.log.Timber
import java.lang.Exception

class CatalogueViewModel : ViewModel() {

    private val animalsCatalogue =
        MutableLiveData<List<WildrModel>>()

    val observableAnimalsCatalogue: LiveData<List<WildrModel>>
        get() = animalsCatalogue

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            WildrManager.findAll(liveFirebaseUser.value?.email!!, animalsCatalogue)
            Timber.i("Catalogue Load Success : ${animalsCatalogue.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("catalogue Load Error : $e.message")
        }
    }

    fun delete(email: String, id: String) {
        try {
            WildrManager.delete(email,id)
            Timber.i("Catalogue Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Catalogue Delete Error : $e.message")
        }
    }
}