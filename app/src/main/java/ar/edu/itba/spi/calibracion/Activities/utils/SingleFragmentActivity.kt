package ar.edu.itba.spi.calibracion.Activities.utils

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import ar.edu.itba.spi.calibracion.Activities.mapActivity.MapActivity
import ar.edu.itba.spi.calibracion.R
import kotlinx.android.synthetic.main.single_fragment.*

abstract class SingleFragmentActivity: AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())

        setContentView(R.layout.single_fragment)
        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fragment = createFragment()
        if (supportFragmentManager.findFragmentById(fragment.id) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }

        ContextCompat.startActivity(this, Intent(this, MapActivity::class.java), null)
        return true

    }
}
