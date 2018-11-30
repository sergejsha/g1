package de.halfbit.g1.base

import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.IdRes
import magnet.Instance

interface Navigator {
    fun navigate(@IdRes action: Int, extras: Bundle? = null)

    companion object {
        const val EXTRA_RESOURCE_PATH = "resource-path"
    }
}

interface NavigationDestination {
    @get:IdRes val action: Int
    fun arrive(extras: Bundle?)
}

@Instance(type = Navigator::class)
internal class DefaultNavigator(
    _destinations: List<NavigationDestination>
) : Navigator {

    private val destinations = SparseArray<NavigationDestination>()
        .apply {
            for (destination in _destinations) {
                check(indexOfKey(destination.action) < 1) { "Multiple destinations not supported. Action $" }
                put(destination.action, destination)
            }
        }

    override fun navigate(action: Int, extras: Bundle?) {
        destinations.get(action)?.arrive(extras)
    }

}
