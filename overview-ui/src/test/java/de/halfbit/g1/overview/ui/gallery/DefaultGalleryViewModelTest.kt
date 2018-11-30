package de.halfbit.g1.overview.ui.gallery

import com.jakewharton.rxrelay2.PublishRelay
import de.halfbit.g1.overview.GithubOverviewService
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
class DefaultGalleryViewModelTest {

    @Mock
    lateinit var view: GalleryView
    @Mock
    lateinit var service: GithubOverviewService
    @Mock
    lateinit var serviceCommand: Consumer<GithubOverviewService.Command>
    @Mock
    lateinit var serviceState: Observable<GithubOverviewService.State>
    @Mock
    lateinit var viewState: Consumer<GalleryView.State>

    private lateinit var viewModel: GalleryViewModel

    @Before
    fun before() {
        viewModel = DefaultGalleryViewModel(service, Schedulers.trampoline())
        whenCalling(view.state).thenReturn(viewState)
        whenCalling(service.command).thenReturn(serviceCommand)
        whenCalling(service.state).thenReturn(serviceState)
    }

    @Test
    fun `LoadNextPage event maps to service command`() {

        // given
        val requests = PublishRelay.create<GalleryView.Request>().toSerialized()
        whenCalling(view.requests).thenReturn(requests)

        val disposables = CompositeDisposable()
        viewModel.bind(view, disposables)

        // when
        requests.accept(GalleryView.Request.LoadNextPage)

        // then
        verify(serviceCommand).accept(GithubOverviewService.Command.LoadNextPage)

    }


}
