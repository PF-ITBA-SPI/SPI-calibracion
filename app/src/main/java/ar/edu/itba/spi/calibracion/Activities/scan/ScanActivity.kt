package ar.edu.itba.spi.calibracion.Activities.scan

import android.net.Uri
import android.support.v4.app.Fragment
import android.util.Log
import ar.edu.itba.spi.calibracion.Activities.SingleFragmentActivity
import ar.edu.itba.spi.calibracion.Activities.scan.fragments.ScanFragment

/**
 * Created by julianrodrigueznicastro on 25/03/2019.
 */

class ScanActivity : SingleFragmentActivity(), ScanFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        Log.i(ar.edu.itba.spi.calibracion.utils.TAG, "Fragment interaction!")
    }

    override fun createFragment(): Fragment {
        return ScanFragment.newInstance()
    }
}