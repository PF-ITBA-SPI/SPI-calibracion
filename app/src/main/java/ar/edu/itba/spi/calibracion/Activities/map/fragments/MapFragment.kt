package ar.edu.itba.spi.calibracion.Activities.map.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ar.edu.itba.spi.calibracion.Activities.map.EXTRA_BUILDING_ID
import ar.edu.itba.spi.calibracion.utils.TAG
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.orhanobut.logger.Logger
import ar.edu.itba.spi.calibracion.Activities.map.MapViewModel
import ar.edu.itba.spi.calibracion.R

/**
 * Main positioning fragment.  Includes a Google Maps fragment, a [FloorSelectorFragment] to
 * manually change floors, and a [StatusIndicatorFragment].
 *
 * - Activities that contain this mapFragment must implement the
 * [MapFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * - Use the [MapFragment.newInstance] factory method to
 * create an instance of this mapFragment.
 *
 */
class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnIndoorStateChangeListener, View.OnClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var buildingId: String
    private val RequestFineLocationPermission = 42

    private var listener: OnFragmentInteractionListener? = null
    private val ITBA = LatLng(-34.602895, -58.368002)
    private val ITBA_NE = LatLng(-34.602866, -58.367693)
    private val ITBA_SW = LatLng(-34.604082, -58.367838)
    private val ITBA_SE = LatLng(-34.604064, -58.367540)
    private var map: GoogleMap? = null
    private lateinit var model: MapViewModel


    private lateinit var mapFragment: SupportMapFragment
    private lateinit var floorSelectorFragment: FloorSelectorFragment
    private lateinit var statusIndicatorFragment: StatusIndicatorFragment

    private lateinit var groundOverlay: GroundOverlay // TODO make this a map of floor number to GroundOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            buildingId = it.getString(EXTRA_BUILDING_ID)
        }

        Log.d(TAG, "Started map super-fragment with building ID $buildingId")

        model = activity?.run {
            ViewModelProviders.of(this).get(MapViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        model.selectedFloorNumber.observe(this, Observer<Int>{ floorNumber ->
            Log.d(TAG, "Selected floor #$floorNumber! From MapFragment.")
            Log.d(TAG, "Removing ground overlay...")
            groundOverlay.remove()
            Log.d(TAG, "Adding new ground overlay...")
            groundOverlay = map!!.addGroundOverlay(GroundOverlayOptions()
                    .position(ITBA_SE, 100f)
                    .bearing(84f)
                    .anchor(1f, 0f)
                    .image(BitmapDescriptorFactory.fromResource(floorPlanResourceId(floorNumber!!)))
            )
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this mapFragment
        val result = inflater.inflate(R.layout.fragment_map, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        floorSelectorFragment = childFragmentManager.findFragmentById(R.id.floorSelectorFragment) as FloorSelectorFragment
        statusIndicatorFragment = childFragmentManager.findFragmentById(R.id.statusIndicatorFragment) as StatusIndicatorFragment
        return result
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val view = getView()
        if(view != null) {
            val fab = view.findViewById(R.id.fab) as FloatingActionButton
            fab.setOnClickListener(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        /*
            Before enabling the My Location layer, we MUST have been granted location permission by
            the user.
         */
        if (ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
//            map.isBuildingsEnabled = true
//            map.isBuildingsEnabled = false
            map.isIndoorEnabled = true
            map.isTrafficEnabled = false
            map.setOnMyLocationButtonClickListener(this)
            map.setOnMyLocationClickListener(this)
            map.setOnIndoorStateChangeListener(this)
            map.uiSettings.isIndoorLevelPickerEnabled = true
            map.mapType = GoogleMap.MAP_TYPE_NORMAL

            // TODO set these based on buildingId
            map.moveCamera(CameraUpdateFactory.newCameraPosition((CameraPosition(ITBA, 18f, 0f, 0f))))
            groundOverlay = map.addGroundOverlay(GroundOverlayOptions()
                    .position(ITBA_SE, 100f)
                    .bearing(84f)
                    .anchor(1f, 0f)
                    .image(BitmapDescriptorFactory.fromResource(floorPlanResourceId(1)))
//                    .anchor(0f, 0f)
            )
        } else {
            // Show rationale and request permission.
            Logger.w("Location permission not granted, requesting")
            ActivityCompat.requestPermissions(this.activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    RequestFineLocationPermission)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RequestFineLocationPermission) {
            if (Manifest.permission.ACCESS_FINE_LOCATION == permissions.getOrNull(0)
                    && PackageManager.PERMISSION_GRANTED == grantResults.getOrNull(0)) {
                this.onMapReady(this.map!!)
            } else {
                // Permission was denied. TODO Display an error message.
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * mapFragment to allow an interaction in this mapFragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this mapFragment using the provided parameters.
         *
         * @param buildingId ID of building to start focused on.
         * @return A new instance of mapFragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(buildingId: String) =
                MapFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_BUILDING_ID, buildingId)
                    }
                }
    }

    override fun onClick(v: View){
        when (v.id) {
            R.id.fab -> {
                map?.let {
                    val target = it.cameraPosition.target
                    Logger.i(target.latitude.toString())
                    Toast.makeText(this.context, "Calibration Submited", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onIndoorBuildingFocused() {
        Log.w(TAG, "Indoor building focused!!")
    }

    override fun onIndoorLevelActivated(p0: IndoorBuilding?) {
        Log.w(TAG, "Indoor level activated on building $p0")
    }

    override fun onMyLocationClick(location: Location) {
        Logger.i("My location clicked!")
        Toast.makeText(this.context, "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Logger.i("My location button clicked!")
        Toast.makeText(this.context, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    /**
     * Gets drawable resource ID given a floor number.
     */
    private fun floorPlanResourceId(floorNum: Int): Int {
        return when (floorNum) {
            0 -> R.drawable.p0
            1 -> R.drawable.p1
            2 -> R.drawable.p2
            3 -> R.drawable.p3
            4 -> R.drawable.p4
            5 -> R.drawable.p5
            6 -> R.drawable.p6
            7 -> R.drawable.p7
            else -> -1
        }
    }
}
