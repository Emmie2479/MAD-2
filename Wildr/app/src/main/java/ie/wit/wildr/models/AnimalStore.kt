package ie.wit.wildr.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface AnimalStore {
    fun findAll(animalsList:
                MutableLiveData<List<AnimalModel>>)
    fun findAll(userid:String,
                animalsList:
                MutableLiveData<List<AnimalModel>>)
    fun findById(userid:String, animalid: String,
                 animal: MutableLiveData<AnimalModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, animal: AnimalModel)
    fun delete(userid:String, animalid: String)
    fun update(userid:String, animalid: String, animal: AnimalModel)
}
