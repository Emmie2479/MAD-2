package ie.wit.wildr.ui.catalogue

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.wildr.R
import ie.wit.wildr.adapters.WildrAdapter
import ie.wit.wildr.adapters.WildrClickListener
import ie.wit.wildr.databinding.FragmentCatalogueBinding
import ie.wit.wildr.main.MainApp
import ie.wit.wildr.models.WildrModel

class CatalogueFragment : Fragment(), WildrClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentCatalogueBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var catalogueViewModel: CatalogueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentCatalogueBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        catalogueViewModel = ViewModelProvider(this).get(CatalogueViewModel::class.java)
        catalogueViewModel.observableAnimalsCatalogue.observe(viewLifecycleOwner, Observer {
                animals ->
            animals?.let { render(animals) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = CatalogueFragmentDirections.actionCatalogueFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_catalogue, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(animalsCatalogue: List<WildrModel>) {
        fragBinding.recyclerView.adapter = WildrAdapter(animalsCatalogue,this)
        if (animalsCatalogue.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.animalsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.animalsNotFound.visibility = View.GONE
        }
    }

    override fun onWildrClick(animal: WildrModel) {
        val action = CatalogueFragmentDirections.actionCatalogueFragmentToDetailFragment(animal.id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}