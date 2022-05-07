package ie.wit.wildr.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface WildrStore {
    fun findAll(animalsCatalogue:
                MutableLiveData<List<WildrModel>>)
    fun findAll(userid:String,
                animalsCatalogue:
                MutableLiveData<List<WildrModel>>)
    fun findById(userid:String, animalid: String,
                 animal: MutableLiveData<WildrModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, animal: WildrModel)
    fun delete(userid:String, animalid: String)
    fun update(userid:String, animalid: String, animal: WildrModel)
}