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
        var edit = false
        binding = ActivityWildrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("animal_edit")) {
            animal = intent.extras?.getParcelable("animal_edit")!!
            binding.animalName.setText(animal.name)
            binding.animalSex.setText(animal.sex)
            binding.btnAdd.setText(R.string.save_animal)
        }

        binding.btnAdd.setOnClickListener() {
            animal.name = binding.animalName.text.toString()
            animal.sex = binding.animalSex.text.toString()
            if (animal.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_animal_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.animals.update(animal.copy())
                } else {
                    app.animals.create(animal.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_animal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}