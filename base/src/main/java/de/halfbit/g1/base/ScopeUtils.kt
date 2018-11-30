package de.halfbit.g1.base

import android.content.Context
import magnet.Scope
import magnet.createSubscope

interface ScopeOwner {
    val scope: Scope
}

fun Context?.createSubscope(initializer: Scope.() -> Unit): Scope {
    val parentScopeOwner = this as? ScopeOwner ?: error("$this is expected to implement ScopeOwner")
    return parentScopeOwner.scope.createSubscope { initializer(this) }
}
