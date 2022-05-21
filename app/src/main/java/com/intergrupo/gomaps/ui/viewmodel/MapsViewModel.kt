package com.intergrupo.gomaps.ui.viewmodel

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intergrupo.gomaps.domain.model.Cities
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.domain.model.States
import com.intergrupo.gomaps.domain.usecase.*
import com.intergrupo.gomaps.util.FlagConstants.ERROR_LOCATION
import com.intergrupo.gomaps.util.FlagConstants.OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val getStatesUseCase: GetStatesUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {

    val statesModel = MutableLiveData<List<States>>()
    val citiesModel = MutableLiveData<List<Cities>>()
    val addressModel = MutableLiveData<List<Address>>()
    val citiesEmptyModel = MutableLiveData<String>()
    val errorModel = MutableLiveData<String>()
    val apiClientFailedModel = MutableLiveData<GoMapsApiFailed>()

    fun getAuthTokenDataBase(countrie: String) {
        viewModelScope.launch {

            val result = getAuthTokenUseCase.getAuthTokenDataBase()
            if (result.isNotEmpty()) getStatisFromApi(result[0].authToken, countrie)

        }
    }

    fun getAuthTokenCitiesDataBase(state: String) {
        viewModelScope.launch {

            val result = getAuthTokenUseCase.getAuthTokenDataBase()
            if (result.isNotEmpty()) getCitiesFromApi(result[0].authToken, state)

        }
    }

    fun getStatisFromApi(authToken: String, countrie: String) {
        viewModelScope.launch {

            val result = getStatesUseCase.getStates("Bearer $authToken", countrie)
            when (result.goMapsApiFailed.code) {

                OK -> if (result.states.isNotEmpty()) statesModel.postValue(result.states)
                else -> apiClientFailedModel.postValue(result.goMapsApiFailed)

            }
        }
    }

    fun getCitiesFromApi(authToken: String, state: String) {
        viewModelScope.launch {

            val result = getCitiesUseCase.getCities("Bearer $authToken", state)
            when (result.goMapsApiFailed.code) {

                OK ->

                    if (result.cities.isNotEmpty()) citiesModel.postValue(result.cities)
                    else citiesEmptyModel.postValue(ERROR_LOCATION)

                else -> apiClientFailedModel.postValue(result.goMapsApiFailed)

            }
        }
    }

    fun getAddress(activity: Activity, capital: String, countrie: String) {
        viewModelScope.launch {

            try {

                var geocoder = Geocoder(activity, Locale.getDefault())
                var direccion = geocoder.getFromLocationName(capital, 2)

                if (direccion.isNotEmpty()) addressModel.postValue(direccion)
                else {
                    geocoder = Geocoder(activity, Locale.getDefault())
                    direccion = geocoder.getFromLocationName(countrie, 2)
                    if (direccion.isNotEmpty()) addressModel.postValue(direccion)
                    else errorModel.postValue(ERROR_LOCATION)
                }

            } catch (ioException: IOException) {
                apiClientFailedModel.postValue(GoMapsApiFailed(code = 500,
                    message = ioException.message.toString(),
                    success = false))
            }
        }
    }

    fun getStatesQuryDataBase(search: String) {
        viewModelScope.launch {

            val result = getStatesUseCase.getStatesQuery(search)
            if (result.isNotEmpty()) statesModel.postValue(result)

        }
    }

    fun getCitiesQuryDataBase(search: String) {
        viewModelScope.launch {

            val result = getCitiesUseCase.getCitiesQuery(search)
            if (result.isNotEmpty()) citiesModel.postValue(result)

        }
    }
}