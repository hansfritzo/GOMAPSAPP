package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.CountriesRepository
import com.intergrupo.gomaps.domain.model.CountiresResponse
import com.intergrupo.gomaps.domain.model.Countries
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCountriesUseCaseTest {

    @RelaxedMockK
    private lateinit var countriesRepository: CountriesRepository

    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCountriesUseCase = GetCountriesUseCase(countriesRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {

        //Given
        val countiresResponse=CountiresResponse(countries = emptyList(), GoMapsApiFailed(code=500, message = "", success = false))
        coEvery { countriesRepository.getcountriesFromApi("123") } returns countiresResponse

        //When
        getCountriesUseCase.getCountries("123")

        //Then
        coVerify(exactly = 1) { countriesRepository.getCoutriesDataBase(countiresResponse.goMapsApiFailed) }
        coVerify(exactly = 0) { countriesRepository.insertList(any())}
        coVerify(exactly = 0) { countriesRepository.clear()}

    }

    @Test
    fun `when list values form api`() = runBlocking {

        //Given
        val countriesList = listOf(Countries(countryName="Colombia", countryShortName="CO",countryPhoneCode=20))
        val countiresResponse=CountiresResponse(countries = countriesList, goMapsApiFailed = GoMapsApiFailed(code=200, message = "", success = true))
        coEvery { countriesRepository.getcountriesFromApi("123") } returns  countiresResponse

        //When
        val response=getCountriesUseCase.getCountries("123")

        //Then
        coVerify(exactly = 0) { countriesRepository.getCoutriesDataBase(GoMapsApiFailed(code=200, message = "", success = true)) }
        coVerify(exactly = 1) { countriesRepository.insertList(any())}
        coVerify(exactly = 1) { countriesRepository.clear()}
        assert(response == countiresResponse)

    }
}