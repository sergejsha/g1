package de.halfbit.g1.overview.ui

import de.halfbit.g1.overview.Repo
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import magnet.Instance

interface OverviewView {

    sealed class Request {
        object LoadNextPage : Request()
    }

    sealed class State {
        object Loading : State()
        class Content(val repos: List<Repo>) : State()
        class Error(val message: String) : State()
    }

    val state: Consumer<State>
    val requests: Observable<Request>

}

@Instance(type = OverviewView::class)
internal class DefaultOverviewView(
    private val androidView: AndroidOverviewView
) : OverviewView {

    override val state: Consumer<OverviewView.State> = Consumer { viewState = it }
    override val requests: Observable<OverviewView.Request> =
        androidView.nextPageRequests.map { OverviewView.Request.LoadNextPage }

    private var viewState: OverviewView.State =
        OverviewView.State.Loading
        set(value) {
            when (value) {
                is OverviewView.State.Error -> androidView.showError(message = value.message)
                is OverviewView.State.Content -> androidView.showContent(value.repos)
            }
            field = value
        }
}
