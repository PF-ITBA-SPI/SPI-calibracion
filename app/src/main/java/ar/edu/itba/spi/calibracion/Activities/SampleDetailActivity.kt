package ar.edu.itba.spi.calibracion.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import ar.edu.itba.spi.calibracion.R
import ar.edu.itba.spi.calibracion.api.ApiSingleton
import ar.edu.itba.spi.calibracion.api.clients.SamplesClient
import ar.edu.itba.spi.calibracion.api.models.Building
import ar.edu.itba.spi.calibracion.api.models.Sample
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sample_detail.*

class SampleDetailActivity : AppCompatActivity() {
    private lateinit var sample : Sample
    private lateinit var building : Building

    private lateinit  var samplesClient: SamplesClient
    private var samplesDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_detail)
        sample = intent.extras!![EXTRA_SAMPLE] as Sample
        building = intent.extras!![EXTRA_BUILDING] as Building
        sampleTitle.text = "${sample.fingerprint.size} APs" // TODO say floor number
        fillTable(sample, content)

        samplesClient = ApiSingleton.getInstance(this).defaultRetrofitInstance.create(SamplesClient::class.java)
        deleteButton.setOnClickListener {
            samplesDisposable = samplesClient
                    .delete(building._id!!, sample._id!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { Log.d(ar.edu.itba.spi.calibracion.utils.TAG, "DELETEing /buildings/${building._id}/${sample._id}") }
                    .subscribe(
                            {
                                Log.i(ar.edu.itba.spi.calibracion.utils.TAG, "Sample deleted, returning")
                                onBackPressed()
                            },
                            { error ->
                                Log.e(ar.edu.itba.spi.calibracion.utils.TAG, "Error DELETEing sample ${sample._id}: ${error.message}")
                                Log.e(ar.edu.itba.spi.calibracion.utils.TAG, error.toString())
                                Log.e(ar.edu.itba.spi.calibracion.utils.TAG, Log.getStackTraceString(error))
                            }
                    )
        }
    }

    /**
     * Fill the given table with detected APs. Generates rows of 3 columns, with:
     * <ol>
     *  <li>BSSID</li>
     *  <li>SSID</li>
     *  <li>RSSI (in dBm)</li>
     * </ol>
     */
    private fun fillTable(sample: Sample, table: TableLayout) {
        sample.fingerprint.entries.forEach { (bssid, rssi) ->
            val row = TableRow(this)
            row.addView(TextView(this).apply { text = bssid; gravity = Gravity.CENTER_HORIZONTAL })
            var ssid = "?"
            if (sample.extraData.containsKey(bssid)) {
                val ssid2 = (sample.extraData[bssid] as Map<String, Any>)["SSID"]
                if (ssid2 != null && ssid2 != "") {
                    ssid = ssid2 as String
                }
            }
            row.addView(TextView(this).apply { text = ssid; gravity = Gravity.CENTER_HORIZONTAL })
            row.addView(TextView(this).apply { text = "$rssi dBm"; gravity = Gravity.CENTER_HORIZONTAL })
            table.addView(row)
        }
    }

    companion object {
        val EXTRA_SAMPLE = "ar.edu.itba.spi.calibracion.activities.SampleDetailActivity.EXTRA_SAMPLE"
        val EXTRA_BUILDING = "ar.edu.itba.spi.calibracion.activities.SampleDetailActivity.EXTRA_BUILDING"

        fun startIntent(context: Context, sample: Sample, building: Building) : Intent {
            return Intent(context, SampleDetailActivity::class.java).apply {
                putExtra(EXTRA_SAMPLE, sample)
                putExtra(EXTRA_BUILDING, building)
            }
        }
    }
}
