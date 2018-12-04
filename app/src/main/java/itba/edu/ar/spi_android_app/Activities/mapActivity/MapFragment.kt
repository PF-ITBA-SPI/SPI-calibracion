package itba.edu.ar.spi_android_app.Activities.mapActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import itba.edu.ar.spi_android_app.Activities.utils.ReactiveFragment
import itba.edu.ar.spi_android_app.R

class MapFragment : ReactiveFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    //View exists here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.map_content, container, false)

}