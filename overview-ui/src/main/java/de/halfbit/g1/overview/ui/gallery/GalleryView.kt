package de.halfbit.g1.overview.ui.gallery

import de.halfbit.g1.overview.Repo
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import magnet.Instance

interface GalleryView {

    sealed class Request {
        object LoadNextPage : Request()
    }

    sealed class State {

        object Loading : State()

        data class Content(
            val lastPage: Int,
            val repos: List<Repo>
        ) : State()

        class Error(
            val message: String
        ) : State()

    }

    val state: Consumer<State>
    val requests: Observable<Request>

}

@Instance(type = GalleryView::class)
internal class DefaultGalleryView(
    private val androidView: AndroidGalleryView
) : GalleryView {

    override val state: Consumer<GalleryView.State> = Consumer { viewState = it }
    override val requests: Observable<GalleryView.Request> =
        androidView.nextPageRequests.map { GalleryView.Request.LoadNextPage }

    private var viewState: GalleryView.State = GalleryView.State.Loading
        set(value) {
            when (value) {
                is GalleryView.State.Error -> androidView.showError(message = value.message)
                is GalleryView.State.Content -> androidView.showContent(value.repos)
            }
            field = value
        }
}
