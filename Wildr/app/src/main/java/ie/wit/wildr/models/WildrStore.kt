package ie.wit.wildr.models

interface WildrStore {
    fun findAll(): List<WildrModel>
    fun create(animal: WildrModel)
    fun update(animal: WildrModel)
    fun delete(animal: WildrModel)
}