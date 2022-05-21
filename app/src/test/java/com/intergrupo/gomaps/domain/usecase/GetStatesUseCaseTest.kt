package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.StatesRepository
import com.intergrupo.gomaps.domain.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetStatesUseCaseTest {

    @RelaxedMockK
    private lateinit var statesRepository: StatesRepository

    private lateinit var getStatesUseCase: GetStatesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getStatesUseCase = GetStatesUseCase(statesRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {

        //Given
        val statesList = listOf(States(stateName = "Bogota"))
        val goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = false)
        val statesResponse = StatesResponse(states = statesList, goMapsApiFailed = goMapsApiFailed)

        coEvery { statesRepository.getStatesFromApi("authToken", "countrie") } returns statesResponse

        //When
        getStatesUseCase.getStates("authToken", "countrie")

        //Then
        coVerify(exactly = 1) { statesRepository.getStatesDataBase(goMapsApiFailed) }
        coVerify(exactly = 1) { statesRepository.getStatesFromApi("authToken", "countrie") }
        coVerify(exactly = 0) { statesRepository.clear() }
        coVerify(exactly = 0) { statesRepository.insertList(any()) }
        coVerify(exactly = 0) { statesRepository.getQueryStatesDataBase(any()) }


    }

    @Test
    fun `when list values form api`() = runBlocking {

        //Given
        val statesList = listOf(States(stateName = "Bogota"))
        val goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true)
        val statesResponse = StatesResponse(states = statesList, goMapsApiFailed = goMapsApiFailed)
        coEvery { statesRepository.getStatesFromApi("authToken", "countrie") } returns statesResponse

        //When
        val response = getStatesUseCase.getStates("authToken", "countrie")

        //Then
        coVerify(exactly = 0) { statesRepository.getStatesDataBase(goMapsApiFailed) }
        coVerify(exactly = 1) { statesRepository.getStatesFromApi("authToken", "countrie") }
        coVerify(exactly = 1) { statesRepository.clear() }
        coVerify(exactly = 1) { statesRepository.insertList(any()) }
        coVerify(exactly = 0) { statesRepository.getQueryStatesDataBase(any()) }
        assert(response == statesResponse)
    }

    @Test
    fun `database token query`() = runBlocking {

        //Given
        val statesList = listOf(States(stateName = "Bogota"))

        coEvery { statesRepository.getQueryStatesDataBase("query") } returns statesList

        //When
        val response = getStatesUseCase.getStatesQuery("query")

        //Then
        coVerify(exactly = 0) { statesRepository.getStatesDataBase(any()) }
        coVerify(exactly = 0) { statesRepository.getStatesFromApi("authToken", "countrie") }
        coVerify(exactly = 0) { statesRepository.clear() }
        coVerify(exactly = 0) { statesRepository.insertList(any()) }
        coVerify(exactly = 1) { statesRepository.getQueryStatesDataBase(any()) }
        assert(response == statesList)
    }
}
