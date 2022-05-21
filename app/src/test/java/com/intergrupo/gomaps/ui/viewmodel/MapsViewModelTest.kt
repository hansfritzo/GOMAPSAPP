package com.intergrupo.gomaps.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intergrupo.gomaps.domain.model.*
import com.intergrupo.gomaps.domain.usecase.GetAuthTokenUseCase
import com.intergrupo.gomaps.domain.usecase.GetCitiesUseCase
import com.intergrupo.gomaps.domain.usecase.GetStatesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MapsViewModelTest {

    @RelaxedMockK
    private lateinit var getAuthTokenUseCase: GetAuthTokenUseCase

    @RelaxedMockK
    private lateinit var getStatesUseCase: GetStatesUseCase

    @RelaxedMockK
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    private lateinit var mapsViewModel: MapsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mapsViewModel = MapsViewModel(getAuthTokenUseCase,getStatesUseCase,getCitiesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }


    @Test
    fun `sync token`() = runTest {
        //Given
        val authTokenList = listOf(AuthToken(authToken = "authToken"))

        coEvery { getAuthTokenUseCase.getAuthTokenDataBase() } returns authTokenList

        mapsViewModel.getAuthTokenDataBase("countrie")

        //Then
        coVerify(exactly = 1) { getAuthTokenUseCase.getAuthTokenDataBase() }
        coVerify(exactly = 0) { getAuthTokenUseCase.getAuthToken() }

    }

    @Test
    fun `sync citis list`() = runTest {
        //Given
        val citiesList = listOf(Cities(cityName = "Colombia"))
        val goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true)
        val citiesResponse = CitiesResponse(cities = citiesList,
            goMapsApiFailed = goMapsApiFailed)

        val authToken = "authToken"


        coEvery { getCitiesUseCase.getCities("Bearer $authToken","state") } returns citiesResponse
        //When

        mapsViewModel.getCitiesFromApi("authToken", "state")

        //Then
        coVerify(exactly = 1) { getCitiesUseCase.getCities("Bearer $authToken","state") }
        coVerify(exactly = 0) { getCitiesUseCase.getCitiesQuery(any()) }

    }

    @Test
    fun `sync states list`() = runTest {
        //Given
        val statesList = listOf(States(stateName = "Colombia"))
        val goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true)
        val statesResponse = StatesResponse(states = statesList,
            goMapsApiFailed = goMapsApiFailed)

        val authToken = "authToken"


        coEvery { getStatesUseCase.getStates("Bearer $authToken", "countries") } returns statesResponse
        //When

        mapsViewModel.getStatisFromApi("authToken", "countries")

        //Then
        coVerify(exactly = 1) { getStatesUseCase.getStates("Bearer $authToken","countries") }
        coVerify(exactly = 0) { getStatesUseCase.getStatesQuery(any()) }

        assert(mapsViewModel.statesModel.value == statesResponse.states)

    }

    @Test
    fun `sync states list error`() = runTest {
        //Given

        val goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = false)
        val statesResponse = StatesResponse(states = emptyList(),
            goMapsApiFailed = goMapsApiFailed)

        val authToken = "authToken"


        coEvery { getStatesUseCase.getStates("Bearer $authToken", "countries") } returns statesResponse
        //When

        mapsViewModel.getStatisFromApi("authToken", "countries")

        //Then
        coVerify(exactly = 1) { getStatesUseCase.getStates("Bearer $authToken","countries") }
        coVerify(exactly = 0) { getStatesUseCase.getStatesQuery(any()) }

        assert(mapsViewModel.apiClientFailedModel.value == statesResponse.goMapsApiFailed)

    }


    @Test
    fun `sync citis list error`() = runTest {
        //Given
        val goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = false)
        val citiesResponse = CitiesResponse(emptyList(),
            goMapsApiFailed = goMapsApiFailed)

        val authToken = "authToken"


        coEvery { getCitiesUseCase.getCities("Bearer $authToken","state") } returns citiesResponse
        //When

        mapsViewModel.getCitiesFromApi("authToken", "state")

        //Then
        coVerify(exactly = 1) { getCitiesUseCase.getCities("Bearer $authToken","state") }
        coVerify(exactly = 0) { getCitiesUseCase.getCitiesQuery(any()) }
        assert(mapsViewModel.apiClientFailedModel.value == citiesResponse.goMapsApiFailed)

    }

    @Test
    fun `search for citi`() = runTest {
        //Given
        val citiesList = listOf(Cities(cityName = "Colombia"))

        coEvery { getCitiesUseCase.getCitiesQuery("search") } returns citiesList

        //When
        mapsViewModel.getCitiesQuryDataBase("search")

        //Then
        coVerify(exactly = 0) { getCitiesUseCase.getCities(any(),any()) }
        coVerify(exactly = 1) { getCitiesUseCase.getCitiesQuery("search") }

        assert(mapsViewModel.citiesModel.value == citiesList)
    }

    @Test
    fun `search for states`() = runTest {
        //Given
        val statesList = listOf(States(stateName = "Colombia"))

        coEvery { getStatesUseCase.getStatesQuery("search") } returns statesList

        //When
        mapsViewModel.getStatesQuryDataBase("search")

        //Then
        coVerify(exactly = 0) { getStatesUseCase.getStates(any(),any()) }
        coVerify(exactly = 1) { getStatesUseCase.getStatesQuery("search") }

        assert(mapsViewModel.statesModel.value == statesList)
    }
}