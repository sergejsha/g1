package de.halfbit.g1.detail

import io.reactivex.Single
import magnet.Instance
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DetailEndpoint {

    @GET("repos/{resource}")
    fun getRepoDetail(@Path(value = "resource", encoded = true) resource: String): Single<JsonRepo>
}

internal data class JsonRepo(
    val name: String,
    val owner: JsonOwner,
    val description: String?,
    val language: String?,
    val stargazers_count: Int?,
    val forks: Int?,
    val watchers: Int?,
    val open_issues: Int?,
    val subscribers_count: Int?
) {
    data class JsonOwner(
        val login: String
    )
}

@Instance(type = DetailEndpoint::class)
internal fun provideDetailEndpoint(retrofit: Retrofit): DetailEndpoint = retrofit.create()
