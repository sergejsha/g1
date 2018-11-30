package de.halfbit.g1.detail.ui

import de.halfbit.g1.detail.RepoDetail
import io.reactivex.functions.Consumer
import magnet.Instance

interface DetailView {
    sealed class State {
        object Loading : State()
        class Content(val repo: RepoDetail) : State()
        class Error(val message: String) : State()
    }

    val state: Consumer<State>
}

@Instance(type = DetailView::class)
internal class DefaultDetailView(
    private val androidDetailView: AndroidDetailView
) : DetailView {

    override val state: Consumer<DetailView.State> = Consumer { viewState = it }

    private var viewState: DetailView.State = DetailView.State.Loading
        set(value) {
            when (value) {
                DetailView.State.Loading -> androidDetailView.showLoading()
                is DetailView.State.Content -> androidDetailView.showContent(value.repo)
                is DetailView.State.Error -> androidDetailView.showError(value.message)
            }
            field = value
        }

}
