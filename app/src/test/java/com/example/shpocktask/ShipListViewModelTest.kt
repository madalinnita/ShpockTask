package com.example.shpocktask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.shpocktask.data.models.PirateShipsResponse
import com.example.shpocktask.data.repositories.ShpockRepository
import com.example.shpocktask.data.utils.CallStatus
import com.example.shpocktask.data.utils.Resource
import com.example.shpocktask.ui.shiplist.ShipListFragmentViewModel
import com.example.shpocktask.utils.testutils.LifeCycleTestOwner
import com.nhaarman.mockitokotlin2.firstValue
import com.nhaarman.mockitokotlin2.lastValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.mock
import org.junit.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ShipListViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val shpockRepository: ShpockRepository = mock()

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private lateinit var shipListViewModel: ShipListFragmentViewModel

    private val pirateShipObserver: Observer<Resource<PirateShipsResponse>> = mock()

    @Captor
    private lateinit var captor: ArgumentCaptor<Resource<PirateShipsResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        shipListViewModel = ShipListFragmentViewModel(shpockRepository)
        shipListViewModel.getPirateShipsResponse().observe(lifeCycleTestOwner, pirateShipObserver)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @Test
    fun `getPirateShips when call from repository then return LOADING and SUCCESS status`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            lifeCycleTestOwner.onResume()
            val pirateShipsResponse = mock<PirateShipsResponse>()
            `when`(shpockRepository.getPirateShips()).thenReturn(pirateShipsResponse)
            shipListViewModel.getPirateShips()
            verify(pirateShipObserver).onChanged(captor.capture())
            Assert.assertEquals(
                captor.firstValue.status,
                CallStatus.LOADING
            )
            verify(pirateShipObserver, times(2)).onChanged(captor.capture())
            Assert.assertEquals(
                captor.lastValue.status,
                CallStatus.SUCCESS
            )
            verify(shpockRepository).getPirateShips()
        }
    }

    @ExperimentalCoroutinesApi
    class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
        TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
        }

    }


}