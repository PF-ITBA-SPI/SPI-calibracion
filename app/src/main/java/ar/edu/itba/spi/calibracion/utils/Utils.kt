package ar.edu.itba.spi.calibracion.utils

import android.app.Activity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by julianrodrigueznicastro on 28/11/2018.
 */

fun SwipeRefreshLayout.stopUI(activity: Activity) = activity.runOnUiThread { this.isRefreshing = false }
