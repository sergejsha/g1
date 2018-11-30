package de.halfbit.g1.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import magnet.Scope
import magnet.bind
import magnet.getOptional

class G1Activity : AppCompatActivity(), ScopeOwner {

    override val scope: Scope by lazy {
        applicationContext.createSubscope {
            bind(this@G1Activity as Context)
            bind(supportFragmentManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            registerMainUiFragment()
        }
    }

    private fun registerMainUiFragment() {
        val fragmentFactory = scope.getOptional<MainUiFragmentFactory>()
        if (fragmentFactory == null) {
            Toast.makeText(this, "Main fragment is not registered", Toast.LENGTH_LONG).show()
            return
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, fragmentFactory.create())
            .commit()
    }

}

interface MainUiFragmentFactory {
    fun create(): Fragment
}
