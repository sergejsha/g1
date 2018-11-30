package de.halfbit.g1.overview

import io.reactivex.Single
import magnet.Instance

interface GithubOverviewSource {
    fun getRepos(page: Int): Single<List<Repo>>
}

@Instance(type = GithubOverviewSource::class)
internal class DefaultGithubOverviewSource(
    private val endpoint: GithubOverviewEndpoint
) : GithubOverviewSource {

    override fun getRepos(page: Int): Single<List<Repo>> =
        endpoint.getRepos(page)
            .map { it.items.map { jsonRepo -> jsonRepo.toRepo() } }

}

private const val UNKNOWN = "---"

private fun JsonRepos.JsonRepo.toRepo(): Repo =
    Repo(
        name = name,
        author = owner.login,
        description = description ?: UNKNOWN,
        language = language ?: UNKNOWN,
        stars = stargazers_count ?: 0
    )
