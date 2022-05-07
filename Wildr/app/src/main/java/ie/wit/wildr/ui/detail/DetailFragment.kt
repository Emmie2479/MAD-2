package ie.wit.wildr.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.wit.wildr.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val args by navArgs<DetailFragmentArgs>()
    private var _fragBinding: FragmentDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.observableAnimal.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render() {
        fragBinding.editType.setText("Type")
        fragBinding.editName.setText("Name")
        fragBinding.editSex.setText("Sex")
        fragBinding.animalvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getAnimal(args.animalid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}