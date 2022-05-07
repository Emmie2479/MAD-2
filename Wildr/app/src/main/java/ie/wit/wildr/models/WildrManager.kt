package ie.wit.wildr.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object WildrManager : WildrStore {

    private val animals = ArrayList<WildrModel>()

    override fun findAll(): List<WildrModel> {
        return animals
    }

    override fun findById(id:Long) : WildrModel? {
        val foundAnimal: WildrModel? = animals.find { it.id == id }
        return foundAnimal
    }

    override fun create(animal: WildrModel) {
        animal.id = getId()
        animals.add(animal)
        logAll()
    }

    fun logAll() {
        Timber.v("** Animals List **")
        animals.forEach { Timber.v("Animal ${it}") }
    }
}