package ie.wit.wildr.ui.list

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
import ie.wit.wildr.R
import ie.wit.wildr.adapters.AnimalAdapter
import ie.wit.wildr.adapters.AnimalClickListener
import ie.wit.wildr.databinding.FragmentListBinding
import ie.wit.wildr.models.AnimalModel
import ie.wit.wildr.ui.auth.LoggedInViewModel
import ie.wit.wildr.utils.*
import timber.log.Timber


class ListFragment : Fragment(), AnimalClickListener {

    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToFormFragment()
            findNavController().navigate(action)
        }
        showLoader(loader, "Downloading Animals")
        listViewModel.observableAnimalsList.observe(viewLifecycleOwner, Observer { animals ->
            animals?.let {
                render(animals as ArrayList<AnimalModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Exercise")
                val adapter = fragBinding.recyclerView.adapter as AnimalAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listViewModel.delete(
                    listViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as AnimalModel).uid!!
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onAnimalClick(viewHolder.itemView.tag as AnimalModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)

        val item = menu.findItem(R.id.toggleAnimals) as MenuItem
        item.setActionView(R.layout.toggle_button)
        val toggleAnimals: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleAnimals.isChecked = false

        toggleAnimals.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) listViewModel.loadAll()
            else listViewModel.load()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun render(animalsList: ArrayList<AnimalModel>) {
        fragBinding.recyclerView.adapter = AnimalAdapter(animalsList, this,
            listViewModel.readOnly.value!!)
        if (animalsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.animalsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.animalsNotFound.visibility = View.GONE
        }
    }

    override fun onAnimalClick(animal: AnimalModel) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(animal.uid!!)

        if(!listViewModel.readOnly.value!!)
            findNavController().navigate(action)
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Animals")
            if(listViewModel.readOnly.value!!)
                listViewModel.loadAll()
            else
                listViewModel.load()
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
                listViewModel.liveFirebaseUser.value = firebaseUser
                listViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}