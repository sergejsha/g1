package de.halfbit.g1.overview.ui

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import de.halfbit.g1.overview.Repo
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import magnet.Classifier
import magnet.Instance
import magnet.Scoping

interface AndroidOverviewView {

    val nextPageRequests: Observable<Unit>

    fun showError(message: String)
    fun showContent(repos: List<Repo>)
}

@Instance(type = AndroidOverviewView::class, scoping = Scoping.UNSCOPED)
internal class DefaultAndroidOverviewView(
    private val overviewItemAdapter: OverviewItemAdapter,
    @Classifier(ROOT) rootView: View
) : AndroidOverviewView {

    override val nextPageRequests = PublishRelay.create<Unit>().toSerialized()

    private val recycler = rootView.findViewById<RecyclerView>(R.id.recycler)
    private val progress = rootView.findViewById<ProgressBar>(R.id.progress)
    private val lazyPager = LazyPager(nextPageRequests)

    init {
        recycler.layoutManager = LinearLayoutManager(rootView.context, RecyclerView.VERTICAL, false)
        recycler.addOnScrollListener(lazyPager)
    }

    override fun showError(message: String) {
        Toast.makeText(recycler.context, "Error: $message", Toast.LENGTH_LONG).show()
    }

    override fun showContent(repos: List<Repo>) {
        if (recycler.visibility != View.VISIBLE) {
            recycler.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }

        val adapter = recycler.adapter as? OverviewItemAdapter ?: overviewItemAdapter
        adapter.setRepos(repos)
        lazyPager.loading = false

        if (recycler.adapter == null) recycler.adapter = adapter
    }

}

internal class LazyPager(
    private val nextPageRequests: Consumer<Unit>
) : RecyclerView.OnScrollListener() {

    var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            recyclerView.layoutManager?.apply {
                val manager = this as LinearLayoutManager
                val position = manager.findLastVisibleItemPosition()
                if (position == RecyclerView.NO_POSITION) {
                    return
                }
                val positionToTriggerPageLoading = itemCount - 1 - 18
                if (position >= positionToTriggerPageLoading) {
                    if (!loading) {
                        loading = true
                        nextPageRequests.accept(Unit)
                    }
                }
            }
        }
    }

}
