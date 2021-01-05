package pt.atp.qrcodereader

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textResult: TextView
    private lateinit var buttonQRCode: Button
    private lateinit var buttonSearch: Button
    var textToSearch: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonQRCode = findViewById(R.id.button_QRCode)
        textResult = findViewById(R.id.textRead)
        buttonSearch = findViewById(R.id.button_search)

        buttonQRCode.setOnClickListener {
            scanQRCode()
        }

        buttonSearch.setOnClickListener {
            search()
        }
    }

    private fun search() {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, textToSearch)
        startActivity(intent)
    }

    private fun scanQRCode(){
        val integrator = IntentIntegrator(this).apply {
            captureActivity = CaptureActivity::class.java
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scanning Code")
        }
        integrator.initiateScan()
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            else{
                //Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                textResult.text = result.contents
                textToSearch = result.contents
                buttonSearch.visibility = View.VISIBLE
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}