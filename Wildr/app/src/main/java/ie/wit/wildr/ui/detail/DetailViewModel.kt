package ie.wit.wildr.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrModel
import timber.log.Timber
import java.lang.Exception

class DetailViewModel : ViewModel() {
    private val animal = MutableLiveData<WildrModel>()

    var observableAnimal: LiveData<WildrModel>
        get() = animal
        set(value) {animal.value = value.value}

    fun getAnimal(email:String, id: String) {
        try {
            WildrManager.findById(email, id, animal)
            Timber.i("Detail getAnimal() Success : ${animal.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getAnimal() Error : $e.message")
        }
    }

    fun updateAnimal(email:String, id: String,animal: WildrModel) {
        try {
            WildrManager.update(email, id, animal)
            Timber.i("Detail update() Success : $animal")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}