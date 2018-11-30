package de.halfbit.g1.overview.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.halfbit.g1.base.MainUiFragmentFactory
import de.halfbit.g1.base.createSubscope
import de.halfbit.g1.overview.ui.gallery.GalleryView
import de.halfbit.g1.overview.ui.gallery.GalleryViewModel
import io.reactivex.disposables.CompositeDisposable
import magnet.Instance
import magnet.bind
import magnet.getSingle

internal const val ROOT = "root"

class OverviewFragment : Fragment() {

    private val scope by lazy {
        activity.createSubscope {
            bind(checkNotNull(view) { "Fragment must return view" }, ROOT)
            bind(CompositeDisposable())
        }
    }

    private lateinit var viewManager: ViewManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.overview_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewManager = scope.getSingle()
    }

    override fun onStart() {
        super.onStart()
        viewManager.bind()
    }

    override fun onPause() {
        viewManager.unbind()
        super.onPause()
    }

}

@Instance(type = ViewManager::class)
internal class ViewManager(
    private val galleryView: GalleryView,
    private val galleryViewModel: GalleryViewModel,
    private val compositeDisposable: CompositeDisposable
) {

    fun bind() {
        galleryViewModel.bind(galleryView, compositeDisposable)
    }

    fun unbind() {
        compositeDisposable.clear()
    }

}

@Instance(type = MainUiFragmentFactory::class)
internal class OverviewFragmentFactory(
    private val context: Context
) : MainUiFragmentFactory {

    override fun create(): Fragment =
        Fragment.instantiate(context, OverviewFragment::class.java.name)

}
