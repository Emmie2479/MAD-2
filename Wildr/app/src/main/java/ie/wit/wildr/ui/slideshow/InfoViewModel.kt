package ie.wit.wildr.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        //value = "This is slideshow Fragment\n testing"
    }
    val text: LiveData<String> = _text
}