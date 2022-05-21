package com.intergrupo.gomaps.ui.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.intergrupo.gomaps.BaseActivity
import com.intergrupo.gomaps.R
import com.intergrupo.gomaps.databinding.SplahsFragmentBinding
import com.intergrupo.gomaps.ui.viewmodel.SplahsViewModel
import com.intergrupo.gomaps.util.FlagConstants.GO_MAPS_API
import com.intergrupo.gomaps.util.FlagConstants.TIME_LOADING
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@AndroidEntryPoint
class SplahsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SplahsFragment()
    }

    private lateinit var viewModel: SplahsViewModel
    private lateinit var binding: SplahsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = SplahsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SplahsViewModel::class.java]

        binding.loading.startShimmer()
        versionControl()
        navigationHome()

    }

    @SuppressLint("SetTextI18n")
    fun versionControl() {
        try {
            val powered = getString(R.string.version)
            val versionName = " " + requireActivity().packageManager
                .getPackageInfo(requireActivity().packageName, 0).versionName
            binding.txtCopyright.text = powered + versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    private fun navigationHome() {
        viewModel.getAuthTokenFromApi()
        viewModel.authTokenModel.observe(this, {

            Handler(Looper.getMainLooper()).postDelayed({

                binding.imgLogoEnd.visibility = View.VISIBLE
                if (binding.loading.isShimmerVisible) {
                    binding.loading.stopShimmer()
                    binding.loading.visibility = View.GONE
                }

                findNavController().navigate(R.id.action_splahsFragment_to_homeActivity)

            }, TIME_LOADING)

        })

        viewModel.apiClientFailedModel.observe(this, { goMapsApiFailed ->

            val bundle = Bundle()
            bundle.putSerializable(GO_MAPS_API, goMapsApiFailed)
            findNavController().navigate(R.id.action_splahsFragment_to_apiClienteFaildFragment,
                bundle)

        })
    }


}
