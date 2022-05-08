package ie.wit.wildr.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.wildr.firebase.FirebaseDBManager
import ie.wit.wildr.models.AnimalModel
import timber.log.Timber
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ListViewModel : ViewModel() {

    private val animalsList =
        MutableLiveData<List<AnimalModel>>()

    val observableAnimalsList: LiveData<List<AnimalModel>>
        get() = animalsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    var searchResults = ArrayList<AnimalModel>()

    init { load() }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                animalsList)
            Timber.i("List Load Success : ${animalsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(animalsList)
            Timber.i("List LoadAll Success : ${animalsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("List Delete Success")
        }
        catch (e: Exception) {
            Timber.i("List Delete Error : $e.message")
        }
    }
}