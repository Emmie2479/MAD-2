package ie.wit.wildr.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.wildr.databinding.ActivityWildrBinding
import ie.wit.wildr.models.WildrModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class WildrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWildrBinding
    var animal = WildrModel()
    val animals = ArrayList<WildrModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Wildr Activity started...")

        binding.btnAdd.setOnClickListener() {
            animal.name = binding.animalName.text.toString()
            animal.sex = binding.animalSex.text.toString()
            if (animal.name.isNotEmpty()) {
                animals.add(animal.copy())
                i("add Button Pressed: ${animal}")
                for (i in animals.indices)
                { i("Animal[$i]:${this.animals[i]}") }
            }
            else {
                Snackbar.make(it,"Please Enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}