package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.CitiesRepository
import com.intergrupo.gomaps.domain.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCitiesUseCaseTest {

    @RelaxedMockK
    private lateinit var citiesRepository: CitiesRepository

    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCitiesUseCase = GetCitiesUseCase(citiesRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {

        //Given
        val citiesList = listOf(Cities(cityName = "Bogota"))
        val goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = false)
        val citiesResponse = CitiesResponse(cities = citiesList, goMapsApiFailed = goMapsApiFailed)

        coEvery { citiesRepository.getCitiesFromApi("authToken", "state") } returns citiesResponse

        //When
        getCitiesUseCase.getCities("authToken", "state")

        //Then
        coVerify(exactly = 1) { citiesRepository.getCitiesDataBase(citiesResponse.goMapsApiFailed) }
        coVerify(exactly = 1) { citiesRepository.getCitiesFromApi("authToken", "state") }
        coVerify(exactly = 0) { citiesRepository.clear() }
        coVerify(exactly = 0) { citiesRepository.insertList(any()) }
        coVerify(exactly = 0) { citiesRepository.getQueryCitiesDataBase(any()) }


    }

    @Test
    fun `when list values form api`() = runBlocking {
        //Given
        val citiesList = listOf(Cities(cityName = "Bogota"))
        val goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true)
        val citiesResponse = CitiesResponse(cities = citiesList, goMapsApiFailed = goMapsApiFailed)
        coEvery { citiesRepository.getCitiesFromApi("authToken", "state") } returns citiesResponse

        //When
        val response = getCitiesUseCase.getCities("authToken", "state")

        //Then
        coVerify(exactly = 1) { citiesRepository.clear() }
        coVerify(exactly = 1) { citiesRepository.insertList(any()) }
        coVerify(exactly = 1) { citiesRepository.getCitiesFromApi("authToken", "state") }
        coVerify(exactly = 0) { citiesRepository.getQueryCitiesDataBase(any()) }
        coVerify(exactly = 0) { citiesRepository.getCitiesDataBase(citiesResponse.goMapsApiFailed) }
        assert(response == citiesResponse)

    }

    @Test
    fun `database token query`() = runBlocking {

        //Given
        val citiesList = listOf(Cities(cityName = "Bogota"))
        coEvery { citiesRepository.getQueryCitiesDataBase("query") } returns citiesList

        //When
        val response = getCitiesUseCase.getCitiesQuery("query")

        //Then
        coVerify(exactly = 1) { citiesRepository.getQueryCitiesDataBase(any()) }
        coVerify(exactly = 0) { citiesRepository.getCitiesDataBase(any()) }
        coVerify(exactly = 0) { citiesRepository.clear() }
        coVerify(exactly = 0) { citiesRepository.insertList(any()) }
        coVerify(exactly = 0) { citiesRepository.getCitiesFromApi(any(), any()) }
        assert(response == citiesList)
    }
}
