package ar.edu.itba.spi.calibracion.Activities.map

import android.net.Uri
import androidx.fragment.app.Fragment
import android.util.Log
import ar.edu.itba.spi.calibracion.Activities.SingleFragmentActivity
import ar.edu.itba.spi.calibracion.Activities.map.fragments.MapFragment
import ar.edu.itba.spi.calibracion.api.models.Building
import ar.edu.itba.spi.calibracion.utils.TAG

/**
 * Parameter name that identifies the building that the map activity should start focused on.
 */
const val EXTRA_BUILDING = "ar.edu.itba.spi.calibracion.extra.BUILDING"

class MapActivity : SingleFragmentActivity(), MapFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        Log.i(TAG, "Fragment interaction!")
    }

    override fun createFragment(): Fragment {
        return MapFragment.newInstance(intent.getSerializableExtra(EXTRA_BUILDING) as Building)
    }
}
