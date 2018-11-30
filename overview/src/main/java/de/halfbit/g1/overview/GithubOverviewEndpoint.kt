package de.halfbit.g1.overview

import io.reactivex.Single
import magnet.Instance
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GithubOverviewEndpoint {

    @GET("search/repositories?q=language:java+language:kotlin+topic:android&sort=stars&order=desc")
    fun getRepos(@Query("page") page: Int): Single<JsonRepos>
}

internal data class JsonRepos(
    val items: List<JsonRepo>
) {

    data class JsonRepo(
        val name: String,
        val owner: JsonOwner,
        val description: String?,
        val language: String?,
        val stargazers_count: Int?
    )

    data class JsonOwner(
        val login: String
    )

}

@Instance(type = GithubOverviewEndpoint::class)
internal fun provideGithubOverviewEndpoint(retrofit: Retrofit): GithubOverviewEndpoint = retrofit.create()
