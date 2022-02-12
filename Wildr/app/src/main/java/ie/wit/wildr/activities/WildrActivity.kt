package ie.wit.wildr.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.wildr.R
import ie.wit.wildr.main.MainApp
import ie.wit.wildr.models.WildrModel
import ie.wit.wildr.databinding.ActivityWildrBinding
import timber.log.Timber.Forest.i

class WildrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWildrBinding
    var animal = WildrModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Wildr Activity started...")
        binding.btnAdd.setOnClickListener() {
            animal.name = binding.animalName.text.toString()
            animal.sex = binding.animalSex.text.toString()
            if (animal.name.isNotEmpty()) {
                app.animals.add(animal.copy())
                i("add Button Pressed: ${animal}")
                for (i in app.animals.indices) {
                    i("Animal[$i]:${this.app.animals[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_animal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}