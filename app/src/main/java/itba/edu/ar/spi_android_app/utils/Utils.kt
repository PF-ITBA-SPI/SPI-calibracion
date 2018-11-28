package itba.edu.ar.spi_android_app.utils

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.Toast

/**
 * Created by julianrodrigueznicastro on 28/11/2018.
 */

fun SwipeRefreshLayout.stopUI(activity: Activity) = activity.runOnUiThread { this.isRefreshing = false }