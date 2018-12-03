package de.halfbit.g1.overview.ui

import com.jakewharton.rxrelay2.PublishRelay
import de.halfbit.g1.overview.OverviewService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenCalling

@RunWith(MockitoJUnitRunner.Strict::class)
class OverviewViewModelTest {

    @Mock
    lateinit var view: OverviewView
    @Mock
    lateinit var service: OverviewService
    @Mock
    lateinit var serviceCommand: Consumer<OverviewService.Command>
    @Mock
    lateinit var serviceState: Observable<OverviewService.State>
    @Mock
    lateinit var viewState: Consumer<OverviewView.State>

    private lateinit var viewModel: OverviewViewModel

    @Before
    fun before() {
        viewModel = DefaultOverviewViewModel(service, Schedulers.trampoline())
        whenCalling(view.state).thenReturn(viewState)
        whenCalling(service.command).thenReturn(serviceCommand)
        whenCalling(service.state).thenReturn(serviceState)
    }

    @Test
    fun `LoadNextPage event maps to service command`() {

        // given
        val requests = PublishRelay.create<OverviewView.Request>().toSerialized()
        whenCalling(view.requests).thenReturn(requests)

        val disposables = CompositeDisposable()
        viewModel.bind(view, disposables)

        // when
        requests.accept(OverviewView.Request.LoadNextPage)

        // then
        verify(serviceCommand).accept(OverviewService.Command.LoadNextPage)
    }


}
