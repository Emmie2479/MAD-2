package ie.wit.wildr.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.wildr.firebase.FirebaseDBManager
import ie.wit.wildr.models.AnimalModel
import timber.log.Timber
import java.lang.Exception

class DetailViewModel : ViewModel() {
    private val animal = MutableLiveData<AnimalModel>()

    var observableAnimal: LiveData<AnimalModel>
        get() = animal
        set(value) {animal.value = value.value}

    fun getAnimal(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, animal)
            Timber.i("Detail getAnimal() Success : ${
                animal.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getAnimal() Error : $e.message")
        }
    }

    fun updateAnimal(userid:String, id: String,animal: AnimalModel) {
        try {

            FirebaseDBManager.update(userid, id, animal)
            Timber.i("Detail update() Success : $animal")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}