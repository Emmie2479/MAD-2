package ie.wit.wildr.ui.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.wildr.models.WildrManager
import ie.wit.wildr.models.WildrModel

class CatalogueViewModel : ViewModel() {

    private val animalsCatalogue = MutableLiveData<List<WildrModel>>()

    val observableAnimalsCatalogue: LiveData<List<WildrModel>>
        get() = animalsCatalogue

    init {
        load()
    }

    fun load() {
        animalsCatalogue.value = WildrManager.findAll()
    }
}