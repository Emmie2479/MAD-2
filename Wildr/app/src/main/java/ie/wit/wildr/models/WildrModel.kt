package ie.wit.wildr.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WildrModel(var id: Long = 0,
                      var name: String = "",
                      var sex: String = "",
                      var image: Uri = Uri.EMPTY) : Parcelable