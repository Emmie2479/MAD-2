package ie.wit.wildr.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrModel

class DetailViewModel : ViewModel() {
    private val animal = MutableLiveData<WildrModel>()

    val observableAnimal: LiveData<WildrModel>
        get() = animal

    fun getAnimal(id: Long) {
        animal.value = WildrManager.findById(id)
    }
}