package de.halfbit.g1.overview.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.halfbit.g1.overview.Repo
import de.halfbit.g1.overview.ui.R
import magnet.Instance
import magnet.Scoping

@Instance(type = GalleryItemAdapter::class, scoping = Scoping.DIRECT)
internal class GalleryItemAdapter : RecyclerView.Adapter<GalleryItemViewHolder>() {

    private var repos = emptyList<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GalleryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.overview_item, parent, false)
        )

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    fun setRepos(repos: List<Repo>) {
        val changedFrom = this.repos.size
        this.repos = repos
        notifyItemRangeChanged(
            changedFrom, (repos.size - changedFrom).coerceAtLeast(0)
        )
    }

}

internal class GalleryItemViewHolder(
    viewRoot: View
) : RecyclerView.ViewHolder(viewRoot) {

    private val name = viewRoot.findViewById<TextView>(R.id.name)
    private val author = viewRoot.findViewById<TextView>(R.id.author)
    private val language = viewRoot.findViewById<TextView>(R.id.language)
    private val description = viewRoot.findViewById<TextView>(R.id.description)
    private val stars = viewRoot.findViewById<TextView>(R.id.stars)

    fun bind(repo: Repo) {
        name.text = repo.name
        author.text = repo.author
        language.text = repo.language
        description.text = repo.description
        stars.text = repo.stars.toString()
    }

}

