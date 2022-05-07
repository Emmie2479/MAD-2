package ie.wit.wildr.models

interface WildrStore {
    fun findAll() : List<WildrModel>
    fun findById(id: Long) : WildrModel?
    fun create(animal: WildrModel)
}