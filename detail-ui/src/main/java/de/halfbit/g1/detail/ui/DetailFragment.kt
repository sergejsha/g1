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
import magnet.bind

class DetailFragment : Fragment() {

    private val scope by lazy {
        activity.createSubscope {
            bind(CompositeDisposable())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.detail_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // todo
    }

}

@Instance(type = NavigationDestination::class)
internal class DetailFragmentDestination(
    private val fragmentManager: FragmentManager
) : NavigationDestination {

    override val action: Int = R.id.navigator_action_detail

    override fun arrive(extras: Bundle?) {
        extras?.getString(Navigator.EXTRA_RESOURCE_PATH)?.let {
            fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, DetailFragment())
                .commit()
        }
    }

}
