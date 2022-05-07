package ie.wit.wildr.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.wildr.models.WildrModel
import ie.wit.wildr.models.WildrStore
import timber.log.Timber


object FirebaseDBManager : WildrStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(animalsCatalogue: MutableLiveData<List<WildrModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, animalsCatalogue: MutableLiveData<List<WildrModel>>) {

        database.child("user-animals").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Wildr error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<WildrModel>()
                    val children = snapshot.children
                    children.forEach {
                        val animal = it.getValue(WildrModel::class.java)
                        localList.add(animal!!)
                    }
                    database.child("user-animals").child(userid)
                        .removeEventListener(this)

                    animalsCatalogue.value = localList
                }
            })
    }

    override fun findById(userid: String, animalid: String, animal: MutableLiveData<WildrModel>) {

        database.child("user-aniamls").child(userid)
            .child(animalid).get().addOnSuccessListener {
                animal.value = it.getValue(WildrModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, animal: WildrModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("animals").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        animal.uid = key
        val animalValues = animal.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/animals/$key"] = animalValues
        childAdd["/user-animals/$uid/$key"] = animalValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, animalid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/animals/$animalid"] = null
        childDelete["/user-animals/$userid/$animalid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, animalid: String, animal: WildrModel) {

        val animalValues = animal.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["donations/$animalid"] = animalValues
        childUpdate["user-animals/$userid/$animalid"] = animalValues

        database.updateChildren(childUpdate)
    }
}