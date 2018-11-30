package de.halfbit.g1.overview

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import org.junit.Before
import org.junit.Test

class GithubOverviewEndpointTest {

    private lateinit var moshi: Moshi

    @Before
    fun before() {
        moshi = Moshi.Builder().build()
    }

    @Test
    fun `JsonRepos corresponds JSON format`() {
        val jsonAdapter = moshi.adapter(JsonRepos::class.java)
        val repos = jsonAdapter.fromJson(TEST_JSON)
        assertThat(repos).isEqualTo(
            JsonRepos(
                items = listOf(
                    JsonRepos.JsonRepo(
                        name = "MoneyTextView",
                        description = "A simple Android TextView to display amounts of money in different formats.",
                        language = "Java",
                        stargazers_count = 448,
                        owner = JsonRepos.JsonOwner(
                            login = "fabiomsr"
                        )
                    ),
                    JsonRepos.JsonRepo(
                        name = "IndicatorDialog",
                        description = "a dialog with arrow indicator in the location where you want",
                        language = "Java",
                        stargazers_count = 449,
                        owner = JsonRepos.JsonOwner(
                            login = "jiang111"
                        )
                    )
                )
            )
        )

    }


}

private const val TEST_JSON = """
{
  "total_count": 34074,
  "incomplete_results": false,
  "items": [
    {
      "id": 45780127,
      "node_id": "MDEwOlJlcG9zaXRvcnk0NTc4MDEyNw==",
      "name": "MoneyTextView",
      "full_name": "fabiomsr/MoneyTextView",
      "private": false,
      "owner": {
        "login": "fabiomsr",
        "id": 5305689,
        "node_id": "MDQ6VXNlcjUzMDU2ODk=",
        "avatar_url": "https://avatars0.githubusercontent.com/u/5305689?v=4",
        "gravatar_id": "",
        "url": "https://api.github.com/users/fabiomsr",
        "html_url": "https://github.com/fabiomsr",
        "followers_url": "https://api.github.com/users/fabiomsr/followers",
        "following_url": "https://api.github.com/users/fabiomsr/following{/other_user}",
        "gists_url": "https://api.github.com/users/fabiomsr/gists{/gist_id}",
        "starred_url": "https://api.github.com/users/fabiomsr/starred{/owner}{/repo}",
        "subscriptions_url": "https://api.github.com/users/fabiomsr/subscriptions",
        "organizations_url": "https://api.github.com/users/fabiomsr/orgs",
        "repos_url": "https://api.github.com/users/fabiomsr/repos",
        "events_url": "https://api.github.com/users/fabiomsr/events{/privacy}",
        "received_events_url": "https://api.github.com/users/fabiomsr/received_events",
        "type": "User",
        "site_admin": false
      },
      "html_url": "https://github.com/fabiomsr/MoneyTextView",
      "description": "A simple Android TextView to display amounts of money in different formats.",
      "fork": false,
      "url": "https://api.github.com/repos/fabiomsr/MoneyTextView",
      "forks_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/forks",
      "keys_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/keys{/key_id}",
      "collaborators_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/collaborators{/collaborator}",
      "teams_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/teams",
      "hooks_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/hooks",
      "issue_events_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/issues/events{/number}",
      "events_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/events",
      "assignees_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/assignees{/user}",
      "branches_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/branches{/branch}",
      "tags_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/tags",
      "blobs_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/git/blobs{/sha}",
      "git_tags_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/git/tags{/sha}",
      "git_refs_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/git/refs{/sha}",
      "trees_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/git/trees{/sha}",
      "statuses_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/statuses/{sha}",
      "languages_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/languages",
      "stargazers_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/stargazers",
      "contributors_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/contributors",
      "subscribers_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/subscribers",
      "subscription_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/subscription",
      "commits_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/commits{/sha}",
      "git_commits_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/git/commits{/sha}",
      "comments_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/comments{/number}",
      "issue_comment_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/issues/comments{/number}",
      "contents_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/contents/{+path}",
      "compare_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/compare/{base}...{head}",
      "merges_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/merges",
      "archive_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/{archive_format}{/ref}",
      "downloads_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/downloads",
      "issues_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/issues{/number}",
      "pulls_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/pulls{/number}",
      "milestones_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/milestones{/number}",
      "notifications_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/notifications{?since,all,participating}",
      "labels_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/labels{/name}",
      "releases_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/releases{/id}",
      "deployments_url": "https://api.github.com/repos/fabiomsr/MoneyTextView/deployments",
      "created_at": "2015-11-08T12:46:34Z",
      "updated_at": "2018-11-29T10:06:29Z",
      "pushed_at": "2017-09-18T21:18:17Z",
      "git_url": "git://github.com/fabiomsr/MoneyTextView.git",
      "ssh_url": "git@github.com:fabiomsr/MoneyTextView.git",
      "clone_url": "https://github.com/fabiomsr/MoneyTextView.git",
      "svn_url": "https://github.com/fabiomsr/MoneyTextView",
      "homepage": "",
      "size": 381,
      "stargazers_count": 448,
      "watchers_count": 448,
      "language": "Java",
      "has_issues": true,
      "has_projects": true,
      "has_downloads": true,
      "has_wiki": true,
      "has_pages": false,
      "forks_count": 63,
      "mirror_url": null,
      "archived": false,
      "open_issues_count": 7,
      "license": null,
      "forks": 63,
      "open_issues": 7,
      "watchers": 448,
      "default_branch": "master",
      "score": 2.4677517
    },
    {
      "id": 91709453,
      "node_id": "MDEwOlJlcG9zaXRvcnk5MTcwOTQ1Mw==",
      "name": "IndicatorDialog",
      "full_name": "jiang111/IndicatorDialog",
      "private": false,
      "owner": {
        "login": "jiang111",
        "id": 3346368,
        "node_id": "MDQ6VXNlcjMzNDYzNjg=",
        "avatar_url": "https://avatars3.githubusercontent.com/u/3346368?v=4",
        "gravatar_id": "",
        "url": "https://api.github.com/users/jiang111",
        "html_url": "https://github.com/jiang111",
        "followers_url": "https://api.github.com/users/jiang111/followers",
        "following_url": "https://api.github.com/users/jiang111/following{/other_user}",
        "gists_url": "https://api.github.com/users/jiang111/gists{/gist_id}",
        "starred_url": "https://api.github.com/users/jiang111/starred{/owner}{/repo}",
        "subscriptions_url": "https://api.github.com/users/jiang111/subscriptions",
        "organizations_url": "https://api.github.com/users/jiang111/orgs",
        "repos_url": "https://api.github.com/users/jiang111/repos",
        "events_url": "https://api.github.com/users/jiang111/events{/privacy}",
        "received_events_url": "https://api.github.com/users/jiang111/received_events",
        "type": "User",
        "site_admin": false
      },
      "html_url": "https://github.com/jiang111/IndicatorDialog",
      "description": "a dialog with arrow indicator in the location where you want",
      "fork": false,
      "url": "https://api.github.com/repos/jiang111/IndicatorDialog",
      "forks_url": "https://api.github.com/repos/jiang111/IndicatorDialog/forks",
      "keys_url": "https://api.github.com/repos/jiang111/IndicatorDialog/keys{/key_id}",
      "collaborators_url": "https://api.github.com/repos/jiang111/IndicatorDialog/collaborators{/collaborator}",
      "teams_url": "https://api.github.com/repos/jiang111/IndicatorDialog/teams",
      "hooks_url": "https://api.github.com/repos/jiang111/IndicatorDialog/hooks",
      "issue_events_url": "https://api.github.com/repos/jiang111/IndicatorDialog/issues/events{/number}",
      "events_url": "https://api.github.com/repos/jiang111/IndicatorDialog/events",
      "assignees_url": "https://api.github.com/repos/jiang111/IndicatorDialog/assignees{/user}",
      "branches_url": "https://api.github.com/repos/jiang111/IndicatorDialog/branches{/branch}",
      "tags_url": "https://api.github.com/repos/jiang111/IndicatorDialog/tags",
      "blobs_url": "https://api.github.com/repos/jiang111/IndicatorDialog/git/blobs{/sha}",
      "git_tags_url": "https://api.github.com/repos/jiang111/IndicatorDialog/git/tags{/sha}",
      "git_refs_url": "https://api.github.com/repos/jiang111/IndicatorDialog/git/refs{/sha}",
      "trees_url": "https://api.github.com/repos/jiang111/IndicatorDialog/git/trees{/sha}",
      "statuses_url": "https://api.github.com/repos/jiang111/IndicatorDialog/statuses/{sha}",
      "languages_url": "https://api.github.com/repos/jiang111/IndicatorDialog/languages",
      "stargazers_url": "https://api.github.com/repos/jiang111/IndicatorDialog/stargazers",
      "contributors_url": "https://api.github.com/repos/jiang111/IndicatorDialog/contributors",
      "subscribers_url": "https://api.github.com/repos/jiang111/IndicatorDialog/subscribers",
      "subscription_url": "https://api.github.com/repos/jiang111/IndicatorDialog/subscription",
      "commits_url": "https://api.github.com/repos/jiang111/IndicatorDialog/commits{/sha}",
      "git_commits_url": "https://api.github.com/repos/jiang111/IndicatorDialog/git/commits{/sha}",
      "comments_url": "https://api.github.com/repos/jiang111/IndicatorDialog/comments{/number}",
      "issue_comment_url": "https://api.github.com/repos/jiang111/IndicatorDialog/issues/comments{/number}",
      "contents_url": "https://api.github.com/repos/jiang111/IndicatorDialog/contents/{+path}",
      "compare_url": "https://api.github.com/repos/jiang111/IndicatorDialog/compare/{base}...{head}",
      "merges_url": "https://api.github.com/repos/jiang111/IndicatorDialog/merges",
      "archive_url": "https://api.github.com/repos/jiang111/IndicatorDialog/{archive_format}{/ref}",
      "downloads_url": "https://api.github.com/repos/jiang111/IndicatorDialog/downloads",
      "issues_url": "https://api.github.com/repos/jiang111/IndicatorDialog/issues{/number}",
      "pulls_url": "https://api.github.com/repos/jiang111/IndicatorDialog/pulls{/number}",
      "milestones_url": "https://api.github.com/repos/jiang111/IndicatorDialog/milestones{/number}",
      "notifications_url": "https://api.github.com/repos/jiang111/IndicatorDialog/notifications{?since,all,participating}",
      "labels_url": "https://api.github.com/repos/jiang111/IndicatorDialog/labels{/name}",
      "releases_url": "https://api.github.com/repos/jiang111/IndicatorDialog/releases{/id}",
      "deployments_url": "https://api.github.com/repos/jiang111/IndicatorDialog/deployments",
      "created_at": "2017-05-18T15:27:47Z",
      "updated_at": "2018-11-23T07:41:01Z",
      "pushed_at": "2018-08-21T09:26:42Z",
      "git_url": "git://github.com/jiang111/IndicatorDialog.git",
      "ssh_url": "git@github.com:jiang111/IndicatorDialog.git",
      "clone_url": "https://github.com/jiang111/IndicatorDialog.git",
      "svn_url": "https://github.com/jiang111/IndicatorDialog",
      "homepage": "",
      "size": 8320,
      "stargazers_count": 449,
      "watchers_count": 448,
      "language": "Java",
      "has_issues": true,
      "has_projects": true,
      "has_downloads": true,
      "has_wiki": true,
      "has_pages": false,
      "forks_count": 62,
      "mirror_url": null,
      "archived": false,
      "open_issues_count": 0,
      "license": null,
      "forks": 62,
      "open_issues": 0,
      "watchers": 448,
      "default_branch": "master",
      "score": 2.4632456
    }
  ]
}
"""
