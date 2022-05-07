package ie.wit.wildr.ui.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.wildr.firebase.FirebaseDBManager
import ie.wit.wildr.models.WildrModel
import timber.log.Timber
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class CatalogueViewModel : ViewModel() {

    private val animalsCatalogue =
        MutableLiveData<List<WildrModel>>()

    val observableAnimalsCatalogue: LiveData<List<WildrModel>>
        get() = animalsCatalogue

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    var searchResults = ArrayList<WildrModel>()

    init { load() }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                animalsCatalogue)
            Timber.i("Catalogue Load Success : ${animalsCatalogue.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Catalogue Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(animalsCatalogue)
            Timber.i("Catalogue LoadAll Success : ${animalsCatalogue.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Catalogue LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Catalogue Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Catalogue Delete Error : $e.message")
        }
    }
}