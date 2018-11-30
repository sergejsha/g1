package de.halfbit.g1.base

import android.app.Application
import android.content.Context
import magnet.Magnet
import magnet.Scope
import magnet.bind
import magnet.getSingle
import magnetx.AppExtension

const val APPLICATION = "application"

class G1App : Application(), ScopeOwner {

    override val scope: Scope by lazy {
        Magnet.createRootScope().apply {
            bind(this@G1App as Application)
            bind(this@G1App as Context, APPLICATION)
        }
    }

    private lateinit var extensions: AppExtension.Delegate

    override fun onCreate() {
        super.onCreate()
        extensions = scope.getSingle()
        extensions.onCreate()
    }

    override fun onTrimMemory(level: Int) {
        extensions.onTrimMemory(level)
        super.onTrimMemory(level)
    }

}
