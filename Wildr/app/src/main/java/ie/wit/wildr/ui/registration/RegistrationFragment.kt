package ie.wit.wildr.ui.registration

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.ObservableInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.wildr.R
import ie.wit.wildr.databinding.FragmentRegistrationBinding
import ie.wit.wildr.models.WildrModel
import ie.wit.wildr.ui.auth.LoggedInViewModel
import ie.wit.wildr.ui.catalogue.CatalogueViewModel
import timber.log.Timber

class RegistrationFragment : Fragment() {

    private var _fragBinding: FragmentRegistrationBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    private lateinit var registrationViewModel: RegistrationViewModel
    private val catalogueViewModel: CatalogueViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        registrationViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        setButtonListener(fragBinding)

        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.wildrError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentRegistrationBinding) {
        layout.btnAdd.setOnClickListener {
            /*(val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                Toast.makeText(context,"Donate Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                layout.totalSoFar.text = String.format(getString(R.string.totalSoFar),totalDonated)
                layout.progressBar.progress = totalDonated
                donateViewModel.addDonation(loggedInViewModel.liveFirebaseUser,
                            DonationModel(paymentmethod = paymentmethod,amount = amount,
                              email = loggedInViewModel.liveFirebaseUser.value?.email!!))
            }*/
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_registration, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }
}