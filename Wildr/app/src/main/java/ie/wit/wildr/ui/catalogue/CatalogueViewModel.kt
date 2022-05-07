package ie.wit.wildr.ui.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.wildr.firebase.FirebaseDBManager
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
            //WildrManager.findAll(liveFirebaseUser.value?.email!!, animalsCatalogue)
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                animalsCatalogue)
            Timber.i("Catalogue Load Success : ${animalsCatalogue.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Catalogue Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            //WildrManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Animal Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Animal Delete Error : $e.message")
        }
    }
}