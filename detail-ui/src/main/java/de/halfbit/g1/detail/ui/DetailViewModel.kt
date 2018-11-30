package de.halfbit.g1.detail.ui

import de.halfbit.g1.detail.GithubDetailService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import magnet.Classifier
import magnet.Instance

interface DetailViewModel {
    fun bind(view: DetailView, disposables: CompositeDisposable)
}

@Instance(type = DetailViewModel::class)
internal class DefaultDetailViewModel(
    private val githubDetailService: GithubDetailService,
    @Classifier(RESOURCE) private val resource: String
) : DetailViewModel {

    init {
        githubDetailService.command
            .accept(GithubDetailService.LoadDetailCommand(resource))
    }

    override fun bind(view: DetailView, disposables: CompositeDisposable) {

        val loading = githubDetailService.state
            .ofType<GithubDetailService.State.Processing>()
            .map { DetailView.State.Loading }

        val error = githubDetailService.state
            .ofType<GithubDetailService.State.Error>()
            .map { DetailView.State.Error(it.err.message ?: it.err.toString()) }

        val content = githubDetailService.state
            .ofType<GithubDetailService.State.Content>()
            .map { DetailView.State.Content(repo = it.repo) }

        disposables += Observable
            .merge(loading, error, content)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view.state)

    }

}
