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

interface OverviewService {

    sealed class Command {
        object LoadNextPage : Command()
    }

    sealed class State {
        object Empty : State()
        data class Content(val lastPage: Int, val repos: List<Repo>) : State()
        class Error(val err: Throwable) : State()
    }

    val state: Observable<State>
    val command: Consumer<Command>

}

@Instance(type = OverviewService::class, disposer = "dispose")
internal class DefaultOverviewService(
    private val overviewSource: OverviewSource
) : OverviewService {

    override val state = BehaviorRelay
        .createDefault<OverviewService.State>(OverviewService.State.Empty)
        .toSerialized()

    override val command = PublishRelay
        .create<OverviewService.Command>()
        .toSerialized()

    private val disposables = CompositeDisposable()

    init {

        disposables += command
            .ofType<OverviewService.Command.LoadNextPage>()
            .withLatestFrom(state)
            .flatMapSingle { loadNextPage(it.second) }
            .subscribe(state)

        command.accept(OverviewService.Command.LoadNextPage)
    }

    private fun loadNextPage(currentState: OverviewService.State): Single<OverviewService.State> {
        val source = when (currentState) {
            is OverviewService.State.Content ->
                overviewSource
                    .getRepos(currentState.lastPage + 1)
                    .map { it.appendTo(currentState) }
            else ->
                overviewSource
                    .getRepos(1)
                    .map { it.toContent() }
        }
        return source
            .onErrorReturn { err: Throwable ->
                err.printStackTrace()
                OverviewService.State.Error(err)
            }
            .subscribeOn(Schedulers.io())
    }

    fun dispose() {
        disposables.dispose()
    }

}

private fun List<Repo>.toContent(): OverviewService.State =
    OverviewService.State.Content(
        lastPage = 1,
        repos = this
    )

private fun List<Repo>.appendTo(currentState: OverviewService.State.Content): OverviewService.State =
    currentState.copy(
        lastPage = currentState.lastPage + 1,
        repos = currentState.repos.toMutableList().also { it.addAll(this) }
    )
