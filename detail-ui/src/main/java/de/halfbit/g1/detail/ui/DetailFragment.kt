package de.halfbit.g1.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import de.halfbit.g1.base.NavigationDestination
import de.halfbit.g1.base.Navigator
import de.halfbit.g1.base.createSubscope
import io.reactivex.disposables.CompositeDisposable
import magnet.Instance
import magnet.Scope
import magnet.bind
import magnet.getSingle

internal const val ROOT = "root"
internal const val RESOURCE = "resource"

class DetailFragment : Fragment() {

    private lateinit var viewManager: ViewManager
    private var scope: Scope? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.detail_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scope = activity.createSubscope {
            bind(checkNotNull(arguments?.getString(RESOURCE)), RESOURCE)
            bind(checkNotNull(view) { "Fragment must create a view" }, ROOT)
            bind(CompositeDisposable())
            viewManager = getSingle()
        }
    }

    override fun onStart() {
        super.onStart()
        viewManager.bind()
    }

    override fun onPause() {
        viewManager.unbind()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope = null
    }

}

@Instance(type = ViewManager::class)
internal class ViewManager(
    private val detailView: DetailView,
    private val detailViewModel: DetailViewModel,
    private val compositeDisposable: CompositeDisposable
) {

    fun bind() {
        detailViewModel.bind(detailView, compositeDisposable)
    }

    fun unbind() {
        compositeDisposable.clear()
    }
}

@Instance(type = NavigationDestination::class)
internal class DetailFragmentDestination(
    private val fragmentManager: FragmentManager
) : NavigationDestination {

    override val action: Int = R.id.navigator_action_detail

    override fun arrive(extras: Bundle?) {
        extras?.getString(Navigator.EXTRA_RESOURCE_PATH)?.let { resource ->
            fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.main_container,
                    DetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(RESOURCE, resource)
                        }
                    })
                .commit()
        }
    }

}
