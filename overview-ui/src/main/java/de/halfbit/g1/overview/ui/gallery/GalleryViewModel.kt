package de.halfbit.g1.overview.ui.gallery

import de.halfbit.g1.overview.GithubOverviewService
import de.halfbit.g1.overview.ui.MAIN_THREAD_SCHEDULER
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import magnet.Classifier
import magnet.Instance

interface GalleryViewModel {
    fun bind(view: GalleryView, disposables: CompositeDisposable)
}

@Instance(type = GalleryViewModel::class)
internal class DefaultGalleryViewModel(
    private val githubOverviewService: GithubOverviewService,
    @Classifier(MAIN_THREAD_SCHEDULER) private val mainThreadScheduler: Scheduler
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
            .observeOn(mainThreadScheduler)
            .subscribe(view.state)

    }

}

private fun GithubOverviewService.State.Error.toViewError() =
    GalleryView.State.Error(message = err.message ?: "Cannot load items")

private fun GithubOverviewService.State.Content.toViewContent() =
    GalleryView.State.Content(repos = repos)
