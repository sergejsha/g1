package de.halfbit.g1.detail.ui

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import de.halfbit.g1.detail.RepoDetail
import magnet.Classifier
import magnet.Instance

interface AndroidDetailView {
    fun showLoading()
    fun showError(message: String)
    fun showContent(repo: RepoDetail)
}

@Instance(type = AndroidDetailView::class)
internal class DefaultAndroidDetailView(
    @Classifier(ROOT) rootView: View
) : AndroidDetailView {

    private val repository = rootView.findViewById<TextView>(R.id.repository)
    private val language = rootView.findViewById<TextView>(R.id.language)
    private val stars = rootView.findViewById<TextView>(R.id.stars)
    private val forks = rootView.findViewById<TextView>(R.id.forks)
    private val subscribers = rootView.findViewById<TextView>(R.id.subscribers)
    private val issues = rootView.findViewById<TextView>(R.id.issues)
    private val description = rootView.findViewById<TextView>(R.id.description)

    private val groupLoading = rootView.findViewById<Group>(R.id.groupLoading)
    private val groupContent = rootView.findViewById<Group>(R.id.groupContent)

    override fun showLoading() {
        groupLoading.visibility = View.VISIBLE
        groupContent.visibility = View.GONE
    }

    override fun showError(message: String) {
        groupLoading.visibility = View.GONE
        groupContent.visibility = View.GONE
        Toast.makeText(repository.context, "Error: $message", Toast.LENGTH_LONG).show()
    }

    override fun showContent(repo: RepoDetail) {
        groupLoading.visibility = View.GONE
        groupContent.visibility = View.VISIBLE

        repository.text = "${repo.author}/${repo.name}"
        language.text = repo.language
        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()
        subscribers.text = repo.subscribers.toString()
        issues.text = repo.issues.toString()
        description.text = repo.description.toString()
    }

}
