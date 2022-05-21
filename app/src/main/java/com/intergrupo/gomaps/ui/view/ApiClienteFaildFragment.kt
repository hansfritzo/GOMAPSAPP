package com.intergrupo.gomaps.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.intergrupo.gomaps.BaseActivity
import com.intergrupo.gomaps.databinding.ApiClienteFaildFragmentBinding
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.util.FlagConstants
import com.intergrupo.gomaps.util.FlagConstants.GO_MAPS_API

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class ApiClienteFaildFragment : BaseFragment() {

    companion object {
        fun newInstance() = ApiClienteFaildFragment()
    }

    private lateinit var binding: ApiClienteFaildFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ApiClienteFaildFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val goMapsApiFailed = arguments!!.getSerializable(GO_MAPS_API) as GoMapsApiFailed

        Glide.with(this).load(getImage(FlagConstants.LOGO_GOMAPS)).into(binding.imgLogo)
        binding.btnLoad.setOnClickListener {

            (requireActivity() as BaseActivity).navigateToSplahs()
        }

        ToastBase(goMapsApiFailed.message)

    }

}