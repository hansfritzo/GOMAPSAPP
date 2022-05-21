package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.AuthtokenRepository
import com.intergrupo.gomaps.domain.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAuthTokenUseCaseTest {

    @RelaxedMockK
    private lateinit var authtokenRepository: AuthtokenRepository

    private lateinit var getAuthTokenUseCase: GetAuthTokenUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getAuthTokenUseCase = GetAuthTokenUseCase(authtokenRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {

        //Given
        val authToken=AuthToken(authToken="")
        val goMapsApiFailed=GoMapsApiFailed(code=500, message = "", success = false)
        val authTokenResponse=AuthTokenResponse(authToken =authToken,goMapsApiFailed=goMapsApiFailed )

        coEvery { authtokenRepository.getAuthtokenFromApi() } returns authTokenResponse

        //When
        getAuthTokenUseCase.getAuthToken()

        //Then
        coVerify(exactly = 1) { authtokenRepository.getTokenDataBase(any()) }
        coVerify(exactly = 0) { authtokenRepository.getTokenDataBase() }
        coVerify(exactly = 0) { authtokenRepository.insert(any())}
        coVerify(exactly = 0) { authtokenRepository.clear()}

    }

    @Test
    fun `when list values form api`() = runBlocking {

        //Given
        val authToken=AuthToken(authToken="123")
        val goMapsApiFailed=GoMapsApiFailed(code=200, message = "", success = true)
        val authTokenResponse=AuthTokenResponse(authToken =authToken,goMapsApiFailed=goMapsApiFailed )

        coEvery { authtokenRepository.getAuthtokenFromApi() } returns authTokenResponse

        //When
        val response=getAuthTokenUseCase.getAuthToken()

        //Then
        coVerify(exactly = 0) { authtokenRepository.getTokenDataBase() }
        coVerify(exactly = 0) { authtokenRepository.getTokenDataBase(any()) }
        coVerify(exactly = 1) { authtokenRepository.insert(any())}
        coVerify(exactly = 1) { authtokenRepository.clear()}
        assert(response == authTokenResponse)

    }

    @Test
    fun `database token query`() = runBlocking {

        //Given
        val authTokenList = listOf(AuthToken(authToken = "123"))
        coEvery { authtokenRepository.getTokenDataBase() } returns authTokenList

        //When
        val response=getAuthTokenUseCase.getAuthTokenDataBase()

        //Then
        coVerify(exactly = 1) { authtokenRepository.getTokenDataBase() }
        coVerify(exactly = 0) { authtokenRepository.getTokenDataBase(any()) }
        coVerify(exactly = 0) { authtokenRepository.insert(any())}
        coVerify(exactly = 0) { authtokenRepository.clear()}
        assert(response == authTokenList)

    }
}