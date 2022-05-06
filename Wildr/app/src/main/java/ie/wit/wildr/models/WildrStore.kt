package ie.wit.wildr.models

import androidx.lifecycle.MutableLiveData

interface WildrStore {
    fun findAll(donationsList:
                MutableLiveData<List<WildrModel>>)
    fun findById(id: String) : WildrModel?
    fun create(donation: WildrModel)
    fun delete(id: String)
}

