package ie.wit.wildr.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class WildrModel(
    var uid: String? = "",
    val type: String = "N/A",
    val name: String = "N/A",
    val sex: String = "N/A",
    var profilepic: String = "",
    var email: String? = "joe@bloggs.com")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "type" to type,
            "name" to name,
            "sex" to sex,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}