package com.intergrupo.gomaps.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.domain.model.QueryCountrie
import com.intergrupo.gomaps.domain.usecase.GetAuthTokenUseCase
import com.intergrupo.gomaps.domain.usecase.GetCountrieCapitalUseCase
import com.intergrupo.gomaps.domain.usecase.GetCountriesQueryUseCase
import com.intergrupo.gomaps.domain.usecase.GetCountriesUseCase
import com.intergrupo.gomaps.util.FlagConstants.API
import com.intergrupo.gomaps.util.FlagConstants.EMPTY
import com.intergrupo.gomaps.util.FlagConstants.OK
import com.intergrupo.gomaps.util.FlagConstants.SYNC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val getCountrieCapitalUseCase: GetCountrieCapitalUseCase,
    private val getCountriesQueryUseCase: GetCountriesQueryUseCase,
) : ViewModel() {

    val countrieModel = MutableLiveData<List<QueryCountrie>>()
    val syncCountrieModel = MutableLiveData<List<QueryCountrie>>()
    val apiClientFailedModel = MutableLiveData<GoMapsApiFailed>()

    fun getAuthTokenDataBase(type: Int) {
        viewModelScope.launch {
            val result = getAuthTokenUseCase.getAuthTokenDataBase()
            if (result.isNotEmpty()) {
                when (type) {

                    SYNC -> getCountriesFromApi(result[0].authToken, SYNC)
                    API -> getCountriesFromApi(result[0].authToken, API)
                    else -> getCountriesFromApi(result[0].authToken, API)

                }
            }
        }

    }

    fun getCountriesFromApi(authToken: String, type: Int) {
        viewModelScope.launch {

            val result = getCountriesUseCase.getCountries("Bearer $authToken")
            when (result.goMapsApiFailed.code) {
                OK -> getCapitalFromApi(type)
                else -> apiClientFailedModel.postValue(result.goMapsApiFailed)
            }
        }
    }

    fun getCapitalFromApi(type: Int) {
        viewModelScope.launch {

            val result = getCountrieCapitalUseCase.getCapitalFormApi()
            when (result.goMapsApiFailed.code) {

                OK -> getCountrieCapital(type)
                else -> apiClientFailedModel.postValue(result.goMapsApiFailed)

            }
        }
    }

    fun getCountrieCapital(type: Int) {
        viewModelScope.launch {

            val result = getCountriesQueryUseCase.getQueryCountries()
            if (result.isNotEmpty()) {

                when (type) {

                    SYNC -> syncCountrieModel.postValue(result)
                    API -> countrieModel.postValue(result)
                    else -> countrieModel.postValue(result)

                }
            }

        }
    }

    fun getCountrieCapitalApi() {
        viewModelScope.launch {
            val result = getCountriesQueryUseCase.getQueryCountries()
            when (result.size) {
                EMPTY -> getAuthTokenDataBase(API)
                else -> {
                    countrieModel.postValue(result)
                    getAuthTokenDataBase(SYNC)
                }

            }

        }
    }

    fun getCountriesQuryDataBase(search: String) {
        viewModelScope.launch {

            val result = getCountriesQueryUseCase.getSearchQueryCountries(search)
            if (result.isNotEmpty()) countrieModel.postValue(result)

        }
    }
}