package itba.edu.ar.spi.calibracion.Activities.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import java.util.*

open class ReactiveFragment: Fragment() {

    val dispBag = ArrayList<Disposable>()
    lateinit var supportActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActivity = activity as AppCompatActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        dispBag.forEach { it.dispose() }
        dispBag.removeAll { true }
    }
}
