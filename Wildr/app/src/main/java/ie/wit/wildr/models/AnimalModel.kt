package ie.wit.wildr.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class AnimalModel(
        var uid: String? = "",
        var type: String = "",
        var name: String = "",
        var sex: String = "",
        var profilepic: String = "",
        val email: String = "")
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