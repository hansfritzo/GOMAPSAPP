package com.intergrupo.gomaps.ui.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.intergrupo.gomaps.R
import com.intergrupo.gomaps.databinding.FragmentMapsBinding
import com.intergrupo.gomaps.domain.model.QueryCountrie
import com.intergrupo.gomaps.domain.model.States
import com.intergrupo.gomaps.ui.viewmodel.MapsViewModel
import com.intergrupo.gomaps.util.FlagConstants.GO_MAPS_API
import com.intergrupo.gomaps.util.FlagConstants.QUERY_COUNTRIE
import com.intergrupo.gomaps.util.FlagConstants.STATE
import com.intergrupo.gomaps.util.FlagConstants.TIME_LOADING
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@AndroidEntryPoint
class MapsFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: MapsViewModel
    private val adapterStates = AdapterStates()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        BottomSheetBehavior.from(binding.bottomsheet).apply {
            peekHeight = 300
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]

        binding.loading.startShimmer()
    }

    @SuppressLint("SetTextI18n")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val queryCountrie = arguments!!.getSerializable(QUERY_COUNTRIE) as QueryCountrie

        viewModel.getAuthTokenDataBase(queryCountrie.countryName)

        viewModel.getAddress(requireActivity(), queryCountrie.capitalName, queryCountrie.countryName)

        binding.menu.txtCapital.text = queryCountrie.countryName.uppercase() + "-" + queryCountrie.capitalName.uppercase()

        binding.menu.imgAtras.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable(QUERY_COUNTRIE, queryCountrie)
            findNavController().navigate(R.id.action_mapsFragment_to_homeFragment, bundle)

        }

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

                    viewModel.getStatesQuryDataBase(s.toString())

                },
                    TIME_LOADING
                )
            }
        })

        adapterStates.itemClicked.observe(this, { state ->

            val bundle = Bundle()
            bundle.putSerializable(STATE, state)
            bundle.putSerializable(QUERY_COUNTRIE, queryCountrie)
            findNavController().navigate(R.id.action_mapsFragment_to_mapsCitiesFragment, bundle)

        })

        viewModel.addressModel.observe(this, {

            if (it.isNotEmpty()) {

                val coordinate = LatLng(it[0].latitude, it[0].longitude)

                val location: CameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 5F
                )
                mMap.animateCamera(location)

                val markerOptions = MarkerOptions().position(coordinate)
                    .title(queryCountrie.capitalName)
                    .snippet(queryCountrie.countryName)
                mMap.addMarker(
                    markerOptions
                )

                mMap.setOnMarkerClickListener {

                    val bundle = Bundle()
                    bundle.putSerializable(STATE, States(stateName = queryCountrie.capitalName))
                    bundle.putSerializable(QUERY_COUNTRIE, queryCountrie)
                    findNavController().navigate(R.id.action_mapsFragment_to_mapsCitiesFragment,
                        bundle)

                    true
                }
            }

        })

        viewModel.statesModel.observe(this, { countries ->

            if (countries.isNotEmpty()) {

                adapterStates.update(countries, requireContext())
                binding.recyStates.layoutManager = GridLayoutManager(
                    requireContext(),
                    1,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                binding.recyStates.adapter = adapterStates
                binding.recyStates.setHasFixedSize(true)

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.recyStates.visibility = View.VISIBLE
                    if (binding.loading.isShimmerVisible) {
                        binding.loading.stopShimmer()
                        binding.loading.visibility = View.GONE
                    }

                },
                   TIME_LOADING
                )
            }
        })

        viewModel.errorModel.observe(this,  {

            ToastBase(it)

        })

        viewModel.apiClientFailedModel.observe(this, { goMapsApiFailed ->

            val bundle = Bundle()
            bundle.putSerializable(GO_MAPS_API, goMapsApiFailed)
            findNavController().navigate(R.id.action_mapsFragment_to_apiClienteFaildFragment2,
                bundle)

        })


    }
}