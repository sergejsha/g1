package de.halfbit.g1.detail

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import magnet.Instance

class RepoDetail(
    val name: String,
    val author: String,
    val description: String,
    val language: String,
    val stars: Int,
    val forks: Int,
    val subscribers: Int,
    val issues: Int
)

interface GithubDetailService {

    class LoadDetailCommand(val resource: String)

    sealed class State {
        object Loading : State()
        class Error(val err: Throwable) : State()

        data class Content(
            val repo: RepoDetail
        ) : State()
    }

    val state: Observable<State>
    val command: Consumer<LoadDetailCommand>
}

@Instance(type = GithubDetailService::class, disposer = "dispose")
internal class DefaultGithubDetailService(
    private val githubDetailSource: GithubDetailSource
) : GithubDetailService {

    override val state = BehaviorRelay
        .create<GithubDetailService.State>()
        .toSerialized()

    override val command = PublishRelay
        .create<GithubDetailService.LoadDetailCommand>()
        .toSerialized()

    private val disposables = CompositeDisposable()

    init {

        disposables += command
            .map { GithubDetailService.State.Loading }
            .subscribe(state)

        disposables += command
            .flatMapSingle { loadResourceDetail(it.resource) }
            .onErrorResumeNext { err: Throwable ->
                err.printStackTrace()
                Observable.just(GithubDetailService.State.Error(err))
            }
            .subscribe(state)

    }

    private fun loadResourceDetail(resource: String): Single<GithubDetailService.State> =
        githubDetailSource
            .getRepoDetail(resource)
            .map { GithubDetailService.State.Content(repo = it) as GithubDetailService.State }
            .subscribeOn(Schedulers.io())

    fun dispose() {
        disposables.dispose()
    }

}
