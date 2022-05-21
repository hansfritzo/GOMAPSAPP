package com.intergrupo.gomaps.ui.view

import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@AndroidEntryPoint
open class BaseFragment : Fragment() {

    fun getImage(imageName: String?): Int {
        return resources.getIdentifier(imageName, "drawable",  requireActivity().packageName)
    }

    fun ToastBase(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
    }

}
