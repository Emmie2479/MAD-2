package ie.wit.wildr.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.wildr.R
import ie.wit.wildr.main.MainApp
import ie.wit.wildr.models.WildrModel
import ie.wit.wildr.databinding.ActivityWildrBinding
import ie.wit.wildr.helpers.showImagePicker
import timber.log.Timber.Forest.i

class WildrActivity : AppCompatActivity() {

    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
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
            binding.animalType.setText(animal.type)
            binding.animalName.setText(animal.name)
            binding.animalSex.setText(animal.sex)
            binding.btnAdd.setText(R.string.save_animal)
            Picasso.get()
                .load(animal.image)
                .into(binding.animalImage)
            if (animal.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_animal_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            animal.type = binding.animalType.text.toString()
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_animal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.animals.delete(animal)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            animal.image = result.data!!.data!!
                            Picasso.get()
                                .load(animal.image)
                                .into(binding.animalImage)
                            binding.chooseImage.setText(R.string.change_animal_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}