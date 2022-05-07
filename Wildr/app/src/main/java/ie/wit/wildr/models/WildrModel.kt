package ie.wit.wildr.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WildrModel(
    var _id: String = "N/A",
    val type: String = "N/A",
    val name: String = "N/A",
    val sex: String = "N/A",
    var email: String = "joe@bloggs.com") : Parcelable