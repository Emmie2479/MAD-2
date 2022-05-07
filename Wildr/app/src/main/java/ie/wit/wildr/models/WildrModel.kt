package ie.wit.wildr.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WildrModel(var id: Long = 0,
                      var type: String = "",
                      var name: String = "",
                      var sex: String = "") : Parcelable