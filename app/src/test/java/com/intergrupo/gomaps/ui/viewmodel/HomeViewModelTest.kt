package com.intergrupo.gomaps.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intergrupo.gomaps.domain.model.*
import com.intergrupo.gomaps.domain.usecase.GetAuthTokenUseCase
import com.intergrupo.gomaps.domain.usecase.GetCountrieCapitalUseCase
import com.intergrupo.gomaps.domain.usecase.GetCountriesQueryUseCase
import com.intergrupo.gomaps.domain.usecase.GetCountriesUseCase
import com.intergrupo.gomaps.util.FlagConstants.SYNC
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @RelaxedMockK
    private lateinit var getAuthTokenUseCase: GetAuthTokenUseCase

    @RelaxedMockK
    private lateinit var getCountrieCapitalUseCase: GetCountrieCapitalUseCase

    @RelaxedMockK
    private lateinit var getCountriesQueryUseCase: GetCountriesQueryUseCase

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(getCountriesUseCase,
            getAuthTokenUseCase,
            getCountrieCapitalUseCase,
            getCountriesQueryUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `consult in the query database of region, countries and capital`() = runTest {
        //Given
        val queryList = listOf(QueryCountrie(capitalName = "capitalName",
            regionName = "regionName",
            countryName = "countryName",
            shortName = "shortName"), QueryCountrie(capitalName = "capitalName",
            regionName = "regionName",
            countryName = "countryName",
            shortName = "shortName"))


        coEvery { getCountriesQueryUseCase.getQueryCountries() } returns queryList

        //When
        homeViewModel.getCountrieCapitalApi()

        //Then
        coVerify(exactly = 1) { getCountriesQueryUseCase.getQueryCountries() }
        coVerify(exactly = 0) { getCountriesQueryUseCase.getSearchQueryCountries(any()) }

        assert(homeViewModel.countrieModel.value == queryList)
    }

    @Test
    fun `search for countries, capital and region`() = runTest {
        //Given
        val queryList = listOf(QueryCountrie(capitalName = "capitalName",
            regionName = "regionName",
            countryName = "countryName",
            shortName = "shortName"), QueryCountrie(capitalName = "capitalName",
            regionName = "regionName",
            countryName = "countryName",
            shortName = "shortName"))

        coEvery { getCountriesQueryUseCase.getSearchQueryCountries("search") } returns queryList

        //When
        homeViewModel.getCountriesQuryDataBase("search")

        //Then
        coVerify(exactly = 0) { getCountriesQueryUseCase.getQueryCountries() }
        coVerify(exactly = 1) { getCountriesQueryUseCase.getSearchQueryCountries(any()) }

        assert(homeViewModel.countrieModel.value == queryList)
    }

    @Test
    fun `sync token`() = runTest {
        //Given
        val authTokenList = listOf(AuthToken(authToken = "capitalName"))

        coEvery { getAuthTokenUseCase.getAuthTokenDataBase() } returns authTokenList

        homeViewModel.getAuthTokenDataBase(SYNC)

        //Then
        coVerify(exactly = 1) { getAuthTokenUseCase.getAuthTokenDataBase() }
        coVerify(exactly = 0) { getAuthTokenUseCase.getAuthToken() }

    }

    @Test
    fun `sync country list`() = runTest {
        //Given
        val countriesList = listOf(Countries(countryName = "Colombia",
            countryShortName = "CO",
            countryPhoneCode = 20))
        val countiresResponse = CountiresResponse(countries = countriesList,
            goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true))
        val authToken = "authToken"


        coEvery { getCountriesUseCase.getCountries("Bearer $authToken") } returns countiresResponse
        //When

        homeViewModel.getCountriesFromApi("authToken", 1)

        //Then
        coVerify(exactly = 1) { getCountriesUseCase.getCountries("Bearer $authToken") }

    }

    @Test
    fun `sync country list error`() = runTest {
        //Given

        val countiresResponse = CountiresResponse(countries = emptyList(),
            goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = true))
        val authToken = "authToken"

        coEvery { getCountriesUseCase.getCountries("Bearer $authToken") } returns countiresResponse
        //When

        homeViewModel.getCountriesFromApi("authToken", 1)

        //Then
        coVerify(exactly = 1) { getCountriesUseCase.getCountries("Bearer $authToken") }
        assert(homeViewModel.apiClientFailedModel.value == countiresResponse.goMapsApiFailed)

    }

    @Test
    fun `sync capital list`() = runTest {

        //Given
        val capitalList = listOf(Capital(capitalName = "capitalName",
            regioName = "regioName",
            shortName = "shortName"))

        val goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true)
        val capitalResponse = CapitalResponse(capitals = capitalList,
            goMapsApiFailed = goMapsApiFailed)


        coEvery { getCountrieCapitalUseCase.getCapitalFormApi() } returns capitalResponse
        //When

        homeViewModel.getCapitalFromApi(1)

        //Then
        coVerify(exactly = 1) { getCountrieCapitalUseCase.getCapitalFormApi() }

    }

    @Test
    fun `sync capital list error`() = runTest {

        //Given

        val goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = false)
        val capitalResponse = CapitalResponse(capitals = emptyList(),
            goMapsApiFailed = goMapsApiFailed)

        coEvery { getCountrieCapitalUseCase.getCapitalFormApi() } returns capitalResponse
        //When

        homeViewModel.getCapitalFromApi(1)

        //Then
        coVerify(exactly = 1) { getCountrieCapitalUseCase.getCapitalFormApi() }

        assert(homeViewModel.apiClientFailedModel.value == capitalResponse.goMapsApiFailed)

    }
}

