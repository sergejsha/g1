package de.halfbit.g1.detail

import io.reactivex.Single
import magnet.Instance

interface GithubDetailSource {
    fun getRepoDetail(resource: String): Single<RepoDetail>
}

@Instance(type = GithubDetailSource::class)
internal class DefaultGithubDetailSource(
    private val endpoint: GithubDetailEndpoint
) : GithubDetailSource {

    override fun getRepoDetail(resource: String): Single<RepoDetail> =
        endpoint.getRepoDetail(resource).map { it.toRepoDetail() }

}

private const val EMPTY = "..."

private fun JsonRepo.toRepoDetail(): RepoDetail =
    RepoDetail(
        name = name,
        author = owner.login,
        description = description ?: EMPTY,
        language = language ?: EMPTY,
        stars = stargazers_count ?: 0,
        forks = forks ?: 0,
        subscribers = subscribers_count ?: 0,
        issues = open_issues ?: 0
    )
