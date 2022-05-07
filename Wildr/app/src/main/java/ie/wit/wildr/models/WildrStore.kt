package ie.wit.wildr.models

import androidx.lifecycle.MutableLiveData

interface WildrStore {
    fun findAll(animalsCatalogue:
                MutableLiveData<List<WildrModel>>)
    fun findAll(email:String,
                animalsCatalogue: MutableLiveData<List<WildrModel>>)
    fun findById(email:String, id: String,
                 animal: MutableLiveData<WildrModel>)
    fun create(animal: WildrModel)
    fun delete(email:String,id: String)
    fun update(email:String,id: String,animal: WildrModel)
}