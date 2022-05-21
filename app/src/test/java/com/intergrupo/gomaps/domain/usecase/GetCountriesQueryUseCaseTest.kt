package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.QueryCountyRepository
import com.intergrupo.gomaps.domain.model.QueryCountrie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCountriesQueryUseCaseTest {

    @RelaxedMockK
    private lateinit var queryCountyRepository: QueryCountyRepository

    private lateinit var getCountriesQueryUseCase: GetCountriesQueryUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCountriesQueryUseCase = GetCountriesQueryUseCase(queryCountyRepository)
    }

    @Test
    fun `get values from database`() = runBlocking {

        //Given
        val queryCountrieList = listOf(QueryCountrie(capitalName = "Bogota",regionName="America",countryName="Colombia",shortName="CO"))

        coEvery { queryCountyRepository.getQueryCountrieDataBase() } returns queryCountrieList

        //When
        val response= getCountriesQueryUseCase.getQueryCountries()

        //Then
        coVerify(exactly = 1) { queryCountyRepository.getQueryCountrieDataBase() }
        coVerify(exactly = 0) { queryCountyRepository.getSearchQueryCountrieDataBase("search") }
        assert(response == queryCountrieList)

    }


    @Test
    fun `database token query`() = runBlocking {

        //Given
        val queryCountrieList = listOf(QueryCountrie(capitalName = "Bogota",regionName="America",countryName="Colombia",shortName="CO"))

        coEvery { queryCountyRepository.getSearchQueryCountrieDataBase("search") } returns queryCountrieList

        //When
        val response= getCountriesQueryUseCase.getSearchQueryCountries("search")

        //Then
        coVerify(exactly = 0) { queryCountyRepository.getQueryCountrieDataBase() }
        coVerify(exactly = 1) { queryCountyRepository.getSearchQueryCountrieDataBase("search") }
        assert(response == queryCountrieList)
    }
}
