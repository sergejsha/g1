package de.halfbit.g1.overview.ui.gallery

import de.halfbit.g1.overview.GithubOverviewService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import magnet.Instance

interface GalleryViewModel {
    fun bind(view: GalleryView, disposables: CompositeDisposable)
}

@Instance(type = GalleryViewModel::class)
internal class ReposGalleryViewModel(
    private val githubOverviewService: GithubOverviewService
) : GalleryViewModel {

    override fun bind(view: GalleryView, disposables: CompositeDisposable) {

        disposables += view.requests
            .ofType<GalleryView.Request.LoadNextPage>()
            .map { GithubOverviewService.Command.LoadNextPage }
            .subscribe(githubOverviewService.command)

        val content = githubOverviewService.state
            .ofType<GithubOverviewService.State.Content>()
            .map { it.toViewContent() }

        val error = githubOverviewService.state
            .ofType<GithubOverviewService.State.Error>()
            .map { it.toViewError() }

        disposables += Observable
            .merge(content, error)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view.state)

    }

}

private fun GithubOverviewService.State.Error.toViewError() =
    GalleryView.State.Error(message = err.message ?: "Cannot load items")

private fun GithubOverviewService.State.Content.toViewContent() =
    GalleryView.State.Content(repos = repos)
