package de.halfbit.g1.overview

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import magnet.Instance

class Repo(
    val name: String,
    val author: String,
    val description: String,
    val stars: Int,
    val language: String
)

interface GithubOverviewService {

    sealed class Command {
        object LoadNextPage : Command()
    }

    sealed class State {

        object Empty : State()
        class Error(val err: Throwable) : State()

        data class Content(
            val lastPage: Int,
            val repos: List<Repo>
        ) : State()
    }

    val state: Observable<State>
    val command: Consumer<Command>

}

@Instance(type = GithubOverviewService::class, disposer = "dispose")
internal class DefaultGithubOverviewService(
    private val githubOverviewSource: GithubOverviewSource
) : GithubOverviewService {

    override val state = BehaviorRelay
        .createDefault<GithubOverviewService.State>(GithubOverviewService.State.Empty)
        .toSerialized()

    override val command = PublishRelay
        .create<GithubOverviewService.Command>()
        .toSerialized()

    private val disposables = CompositeDisposable()

    init {

        disposables += command
            .ofType<GithubOverviewService.Command.LoadNextPage>()
            .withLatestFrom(state)
            .flatMapSingle { loadNextPage(it.second) }
            .onErrorResumeNext { err: Throwable ->
                err.printStackTrace()
                Observable.just(GithubOverviewService.State.Error(err))
            }
            .subscribe(state)

        command.accept(GithubOverviewService.Command.LoadNextPage)
    }

    private fun loadNextPage(currentState: GithubOverviewService.State): Single<GithubOverviewService.State> =
        when (currentState) {
            is GithubOverviewService.State.Content -> {
                githubOverviewSource
                    .getRepos(currentState.lastPage + 1)
                    .map { it.appendTo(currentState) }
                    .subscribeOn(Schedulers.io())
            }
            else -> {
                githubOverviewSource
                    .getRepos(1)
                    .map { it.toContent() }
                    .subscribeOn(Schedulers.io())
            }
        }

    fun dispose() {
        disposables.dispose()
    }

}

private fun List<Repo>.toContent(): GithubOverviewService.State =
    GithubOverviewService.State.Content(
        lastPage = 1,
        repos = this
    )

private fun List<Repo>.appendTo(currentState: GithubOverviewService.State.Content): GithubOverviewService.State =
    currentState.copy(
        lastPage = currentState.lastPage + 1,
        repos = currentState.repos.toMutableList().also { it.addAll(this) }
    )
