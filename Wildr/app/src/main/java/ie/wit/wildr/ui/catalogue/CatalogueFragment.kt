package ie.wit.wildr.ui.catalogue

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextWatcher
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.donationx.utils.SwipeToEditCallback
import ie.wit.wildr.R
import ie.wit.wildr.adapters.WildrAdapter
import ie.wit.wildr.adapters.WildrClickListener
import ie.wit.wildr.databinding.FragmentCatalogueBinding
import ie.wit.wildr.models.WildrModel
import ie.wit.wildr.ui.auth.LoggedInViewModel
import ie.wit.wildr.utils.*
import timber.log.Timber


class CatalogueFragment : Fragment(), WildrClickListener {

    private var _fragBinding: FragmentCatalogueBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val catalogueViewModel: CatalogueViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

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
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = CatalogueFragmentDirections.actionCatalogueFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
        showLoader(loader, "Downloading Animals")
        catalogueViewModel.observableAnimalsCatalogue.observe(viewLifecycleOwner, Observer { animals ->
            animals?.let {
                render(animals as ArrayList<WildrModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Animal")
                val adapter = fragBinding.recyclerView.adapter as WildrAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                catalogueViewModel.delete(
                    catalogueViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as WildrModel).uid!!
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onWildrClick(viewHolder.itemView.tag as WildrModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_catalogue, menu)

        val item = menu.findItem(R.id.toggleAnimals) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleAnimals: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleAnimals.isChecked = false

        toggleAnimals.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) catalogueViewModel.loadAll()
            else catalogueViewModel.load()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun render(animalsCatalogue: ArrayList<WildrModel>) {
        fragBinding.recyclerView.adapter = WildrAdapter(animalsCatalogue, this,
            catalogueViewModel.readOnly.value!!)
        if (animalsCatalogue.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.animalsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.animalsNotFound.visibility = View.GONE
        }
    }

    override fun onWildrClick(animal: WildrModel) {
        val action = CatalogueFragmentDirections.actionCatalogueFragmentToDetailFragment(animal.uid!!)

        if(!catalogueViewModel.readOnly.value!!)
            findNavController().navigate(action)
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Animals")
            if(catalogueViewModel.readOnly.value!!)
                catalogueViewModel.loadAll()
            else
                catalogueViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Animals")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                catalogueViewModel.liveFirebaseUser.value = firebaseUser
                catalogueViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}