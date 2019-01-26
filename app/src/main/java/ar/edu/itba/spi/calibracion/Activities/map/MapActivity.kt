package ar.edu.itba.spi.calibracion.Activities.map

import android.net.Uri
import android.support.v4.app.Fragment
import android.util.Log
import ar.edu.itba.spi.calibracion.Activities.SingleFragmentActivity
import ar.edu.itba.spi.calibracion.Activities.map.fragments.MapFragment
import ar.edu.itba.spi.calibracion.utils.TAG

/**
 * Parameter name that identifies the building that the map activity should start focused on.
 */
const val EXTRA_BUILDING_ID = "ar.edu.itba.spi.calibracion.extra.BUILDING_ID"

class MapActivity : SingleFragmentActivity(), MapFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        Log.i(TAG, "Fragment interaction!")
    }

    override fun createFragment(): Fragment {
        return MapFragment.newInstance(intent.getStringExtra(EXTRA_BUILDING_ID))
    }
}
