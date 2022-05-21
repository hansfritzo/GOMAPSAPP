package com.intergrupo.gomaps.ui.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.intergrupo.gomaps.R
import com.intergrupo.gomaps.databinding.HomeFragmentBinding
import com.intergrupo.gomaps.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.EditText
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.intergrupo.gomaps.util.FlagConstants.GO_MAPS_API
import com.intergrupo.gomaps.util.FlagConstants.QUERY_COUNTRIE
import com.intergrupo.gomaps.util.FlagConstants.TIME_LOADING

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private val adapterCountries = AdapterCountries()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint( "NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getCountrieCapitalApi()

        binding.loading.startShimmer()

        val id: Int = binding.menuSearch.search.context.resources
            .getIdentifier("android:id/search_src_text", null, null)

        val editBusqueda = binding.menuSearch.search.findViewById<View>(id) as EditText

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editBusqueda.setTextColor(requireActivity().getColor(R.color.black))
        }

        editBusqueda.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {

                Handler(Looper.getMainLooper()).postDelayed({

                    viewModel.getCountriesQuryDataBase(s.toString())

                },
                    TIME_LOADING
                )
            }
        })

        adapterCountries.itemClicked.observe(this, { queryCountrie ->

            val bundle = Bundle()
            bundle.putSerializable(QUERY_COUNTRIE, queryCountrie)
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment, bundle)

        })

        viewModel.countrieModel.observe(this, { countries ->

            if (countries.isNotEmpty()) {

                adapterCountries.update(countries, requireContext())

                binding.recyCountries.layoutManager = GridLayoutManager(
                    requireContext(),
                    1,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                binding.recyCountries.adapter = adapterCountries
                binding.recyCountries.setHasFixedSize(true)

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.recyCountries.visibility = View.VISIBLE
                    if (binding.loading.isShimmerVisible) {
                        binding.loading.stopShimmer()
                        binding.loading.visibility = View.GONE
                    }
                },
                    TIME_LOADING
                )
            }
        })

        viewModel.syncCountrieModel.observe(this, { countries ->

            if (countries.isNotEmpty()) {

                adapterCountries.update(countries, requireContext())
                adapterCountries.notifyDataSetChanged()

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.recyCountries.visibility = View.VISIBLE
                    if (binding.loading.isShimmerVisible) {
                        binding.loading.stopShimmer()
                        binding.loading.visibility = View.GONE
                    }
                },
                    TIME_LOADING
                )
            }
        })

        viewModel.apiClientFailedModel.observe(this, { goMapsApiFailed ->

            val bundle = Bundle()
            bundle.putSerializable(GO_MAPS_API, goMapsApiFailed)
            findNavController().navigate(R.id.action_homeFragment_to_apiClienteFaildFragment2,
                bundle)

        })


    }

}