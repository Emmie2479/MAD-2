package ie.wit.wildr.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.wildr.firebase.FirebaseDBManager
import ie.wit.wildr.models.WildrModel

class RegistrationViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addAnimal(firebaseUser: MutableLiveData<FirebaseUser>,
                    animal: WildrModel) {
        status.value = try {
            //WIldrManager.create(animal)
            FirebaseDBManager.create(firebaseUser,animal)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}