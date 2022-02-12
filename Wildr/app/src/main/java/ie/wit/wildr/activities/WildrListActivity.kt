package ie.wit.wildr.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.wildr.R
import ie.wit.wildr.adapters.WildrAdapter
import ie.wit.wildr.adapters.WildrListener
import ie.wit.wildr.databinding.ActivityWildrListBinding
import ie.wit.wildr.main.MainApp
import ie.wit.wildr.models.WildrModel

class WildrListActivity : AppCompatActivity(), WildrListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWildrListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WildrAdapter(app.animals.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WildrActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWildrClick(animal: WildrModel) {
        val launcherIntent = Intent(this, WildrActivity::class.java)
        launcherIntent.putExtra("animal_edit", animal)
        startActivityForResult(launcherIntent,0)
    }
}