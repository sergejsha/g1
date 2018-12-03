package de.halfbit.g1.detail.ui

import de.halfbit.g1.detail.DetailService
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
    private val detailService: DetailService,
    @Classifier(RESOURCE) private val resource: String
) : DetailViewModel {

    init {
        detailService.command
            .accept(DetailService.LoadDetailCommand(resource))
    }

    override fun bind(view: DetailView, disposables: CompositeDisposable) {

        val loading = detailService.state
            .ofType<DetailService.State.Processing>()
            .map { DetailView.State.Loading }

        val error = detailService.state
            .ofType<DetailService.State.Error>()
            .map { DetailView.State.Error(it.err.message ?: it.err.toString()) }

        val content = detailService.state
            .ofType<DetailService.State.Content>()
            .map { DetailView.State.Content(repo = it.repo) }

        disposables += Observable
            .merge(loading, error, content)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view.state)

    }

}
