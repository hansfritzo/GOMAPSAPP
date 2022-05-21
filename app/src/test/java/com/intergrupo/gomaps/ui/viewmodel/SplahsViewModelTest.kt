package com.intergrupo.gomaps.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intergrupo.gomaps.domain.model.AuthToken
import com.intergrupo.gomaps.domain.model.AuthTokenResponse
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.domain.usecase.GetAuthTokenUseCase
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
class SplahsViewModelTest {

    @RelaxedMockK
    private lateinit var getAuthTokenUseCase: GetAuthTokenUseCase

    private lateinit var splahsViewModel: SplahsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        splahsViewModel = SplahsViewModel(getAuthTokenUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get api download security token`() = runTest {
        //Given
        val authToken = AuthToken("authToken")
        val goMapsApiFailed = GoMapsApiFailed(code = 200, message = "", success = true)
        val authTokenResponse =
            AuthTokenResponse(authToken = authToken, goMapsApiFailed = goMapsApiFailed)

        coEvery { getAuthTokenUseCase.getAuthToken() } returns authTokenResponse

        //When
        splahsViewModel.getAuthTokenFromApi()

        //Then
        coVerify(exactly = 1) { getAuthTokenUseCase.getAuthToken() }
        coVerify(exactly = 0) { getAuthTokenUseCase.getAuthTokenDataBase() }
        assert(splahsViewModel.authTokenModel.value == authTokenResponse.authToken)
    }

    @Test
    fun `token download error`() = runTest {
        //Given
        val authToken = AuthToken("")
        val goMapsApiFailed = GoMapsApiFailed(code = 500, message = "", success = false)
        val authTokenResponse =
            AuthTokenResponse(authToken = authToken, goMapsApiFailed = goMapsApiFailed)

        coEvery { getAuthTokenUseCase.getAuthToken() } returns authTokenResponse

        //When
        splahsViewModel.getAuthTokenFromApi()

        //Then
        coVerify(exactly = 1) { getAuthTokenUseCase.getAuthToken() }
        coVerify(exactly = 0) { getAuthTokenUseCase.getAuthTokenDataBase() }
        assert(splahsViewModel.apiClientFailedModel.value == authTokenResponse.goMapsApiFailed)

    }
}