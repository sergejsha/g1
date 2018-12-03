package de.halfbit.g1.overview.ui

import de.halfbit.g1.overview.OverviewService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import magnet.Classifier
import magnet.Instance

interface OverviewViewModel {
    fun bind(view: OverviewView, disposables: CompositeDisposable)
}

@Instance(type = OverviewViewModel::class)
internal class DefaultOverviewViewModel(
    private val overviewService: OverviewService,
    @Classifier(MAIN_THREAD_SCHEDULER) private val mainThreadScheduler: Scheduler
) : OverviewViewModel {

    override fun bind(view: OverviewView, disposables: CompositeDisposable) {

        disposables += view.requests
            .ofType<OverviewView.Request.LoadNextPage>()
            .map { OverviewService.Command.LoadNextPage }
            .subscribe(overviewService.command)

        val content = overviewService.state
            .ofType<OverviewService.State.Content>()
            .map { it.toViewContent() }

        val error = overviewService.state
            .ofType<OverviewService.State.Error>()
            .map { it.toViewError() }

        disposables += Observable
            .merge(content, error)
            .observeOn(mainThreadScheduler)
            .subscribe(view.state)

    }

}

private fun OverviewService.State.Error.toViewError() =
    OverviewView.State.Error(message = err.message ?: "Cannot load items")

private fun OverviewService.State.Content.toViewContent() =
    OverviewView.State.Content(repos = repos)
