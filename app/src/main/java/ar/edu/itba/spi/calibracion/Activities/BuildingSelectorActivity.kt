package ar.edu.itba.spi.calibracion.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import ar.edu.itba.spi.calibracion.Activities.map.EXTRA_BUILDING_ID
import ar.edu.itba.spi.calibracion.Activities.map.MapActivity
import ar.edu.itba.spi.calibracion.R
import kotlinx.android.synthetic.main.activity_building_selector.*

class BuildingSelectorActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var selectedBuildingName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_selector)

        // Create an adapter to populate and style the spinner
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter.addAll("ITBA Madero", "ITBA Patricios", "Edificio X") // TODO get these from network
        buildings_spinner.adapter = adapter
        buildings_spinner.onItemSelectedListener = this

        button.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java).apply { putExtra(EXTRA_BUILDING_ID, selectedBuildingName) })
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedBuildingName = parent!!.getItemAtPosition(position) as String
        button.isEnabled = true
        Log.d(ar.edu.itba.spi.calibracion.utils.TAG, "Selected $selectedBuildingName")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedBuildingName = null
        button.isEnabled = false
        Log.d(ar.edu.itba.spi.calibracion.utils.TAG, "Selected nothing!")
    }
}
