package ar.edu.itba.spi.calibracion.Activities.mapActivity

import android.net.Uri
import android.support.v4.app.Fragment
import android.util.Log
import ar.edu.itba.spi.calibracion.Activities.utils.SingleFragmentActivity
import ar.edu.itba.spi.calibracion.Activities.mapActivity.fragments.MapFragment
import ar.edu.itba.spi.calibracion.utils.TAG

class MapActivity : SingleFragmentActivity(), MapFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        Log.i(TAG, "Fragment interaction!")
    }

    override fun createFragment(): Fragment {
        return MapFragment.newInstance("", "")
    }
}
