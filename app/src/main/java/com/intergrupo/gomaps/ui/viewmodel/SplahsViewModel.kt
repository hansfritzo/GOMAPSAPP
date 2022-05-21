package com.intergrupo.gomaps.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intergrupo.gomaps.domain.model.AuthToken
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.domain.usecase.GetAuthTokenUseCase
import com.intergrupo.gomaps.util.FlagConstants.OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@HiltViewModel
class SplahsViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
) : ViewModel() {

    val authTokenModel = MutableLiveData<AuthToken>()
    val apiClientFailedModel = MutableLiveData<GoMapsApiFailed>()
    fun getAuthTokenFromApi() {
        viewModelScope.launch {

            val result = getAuthTokenUseCase.getAuthToken()
            when (result.goMapsApiFailed.code) {

                OK ->
                    if (result.authToken.authToken.isNotEmpty()) authTokenModel.postValue(result.authToken)
                    else apiClientFailedModel.postValue(result.goMapsApiFailed)

                else -> apiClientFailedModel.postValue(result.goMapsApiFailed)
            }
        }
    }
}