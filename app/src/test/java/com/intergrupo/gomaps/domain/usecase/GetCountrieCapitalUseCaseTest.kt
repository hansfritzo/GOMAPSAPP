package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.CountrieCapitalRepository
import com.intergrupo.gomaps.domain.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCountrieCapitalUseCaseTest  {

    @RelaxedMockK
    private lateinit var countrieCapitalRepository: CountrieCapitalRepository

    private lateinit var getCountrieCapitalUseCase: GetCountrieCapitalUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCountrieCapitalUseCase = GetCountrieCapitalUseCase(countrieCapitalRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {

        //Given

        val goMapsApiFailed= GoMapsApiFailed(code=500, message = "", success = false)
        val capitalResponse= CapitalResponse(capitals = emptyList(),goMapsApiFailed=goMapsApiFailed )

        coEvery { countrieCapitalRepository.getCapitalFromApi() } returns capitalResponse

        //When
        getCountrieCapitalUseCase.getCapitalFormApi()

        //Then
        coVerify(exactly = 1) { countrieCapitalRepository.getCapitalFromApi() }
        coVerify(exactly = 1) { countrieCapitalRepository.getCoutriesDataBase(capitalResponse.goMapsApiFailed) }
        coVerify(exactly = 0) { countrieCapitalRepository.insertList(any())}
        coVerify(exactly = 0) { countrieCapitalRepository.clear()}

    }

    @Test
    fun `when list values form api`() = runBlocking {

        val capitalList = listOf(Capital(capitalName = "Bogota",regioName="Colombia",shortName="CO"))
        val goMapsApiFailed= GoMapsApiFailed(code=200, message = "", success = true)
        val capitalResponse= CapitalResponse(capitals = capitalList,goMapsApiFailed=goMapsApiFailed )

        coEvery { countrieCapitalRepository.getCapitalFromApi() } returns capitalResponse

        //When
        val response= getCountrieCapitalUseCase.getCapitalFormApi()

        //Then
        coVerify(exactly = 1) { countrieCapitalRepository.getCapitalFromApi() }
        coVerify(exactly = 1) { countrieCapitalRepository.insertList(any())}
        coVerify(exactly = 1) { countrieCapitalRepository.clear()}
        coVerify(exactly = 0) { countrieCapitalRepository.getCoutriesDataBase(capitalResponse.goMapsApiFailed) }
        assert(response == capitalResponse)
    }

}