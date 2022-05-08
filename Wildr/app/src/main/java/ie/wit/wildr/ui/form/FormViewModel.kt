package ie.wit.wildr.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.wildr.firebase.FirebaseDBManager
import ie.wit.wildr.firebase.FirebaseImageManager
import ie.wit.wildr.models.AnimalModel

class FormViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addAnimal(firebaseUser: MutableLiveData<FirebaseUser>,
                    animal: AnimalModel) {
        status.value = try {
            animal.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser,animal)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

}