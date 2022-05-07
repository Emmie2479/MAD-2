package ie.wit.wildr.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrModel

class RegistrationViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addAnimal(animal: WildrModel) {
        status.value = try {
            WildrManager.create(animal)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}