package ie.wit.wildr.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.wildr.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "animals.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<WildrModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class WildrJSONStore(private val context: Context) : WildrStore {

    var animals = mutableListOf<WildrModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<WildrModel> {
        logAll()
        return animals
    }

    override fun create(animal: WildrModel) {
        animal.id = generateRandomId()
        animals.add(animal)
        serialize()
    }


    override fun update(animal: WildrModel) {
        val animalsList = findAll() as ArrayList<WildrModel>
        var foundAnimal: WildrModel? = animalsList.find { p -> p.id == animal.id }
        if (foundAnimal != null) {
            foundAnimal.name = animal.name
            foundAnimal.sex = animal.sex
            foundAnimal.image = animal.image
        }
        serialize()
    }

    override fun delete(animal: WildrModel) {
        animals.remove(animal)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(animals, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        animals = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        animals.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}