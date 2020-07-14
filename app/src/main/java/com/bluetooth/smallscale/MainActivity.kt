package com.bluetooth.smallscale

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private var deviceId: String = ""
    private val FILE_NAME: String = "EncounterLog.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check for permissions
        var reqPermissions: Array<String> = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, reqPermissions, 0)

        findViewById<Button>(R.id.update_id_button).setOnClickListener {
            deviceId = findViewById<TextInputEditText>(R.id.newId).text.toString()
            findViewById<TextView>(R.id.deviceIdText).text = "ID: "+deviceId
            writeFile(it, FILE_NAME, "hahahahahahahaha")
            pushMsg(deviceId)
            Snackbar.make(it, deviceId, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun isExternalStorageWritable(): Boolean {
        return if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State","Yes, it is writable!")
            true
        } else {
            false
        }
    }

    // Write the given message to /Download/fname
    fun writeFile(view: View, fname: String, msg: String) {
        if(isExternalStorageWritable()) {
            val newMsg: String = "\n"+msg
            var myFile: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fname)
            var fstream: FileOutputStream = FileOutputStream(myFile, true)
            fstream.write(newMsg.toByteArray())
            fstream.close()
        }
    }

    // Write the given message to the top of the message log
    fun pushMsg(msg: String) {
        findViewById<TextView>(R.id.recentMeg4).text = findViewById<TextView>(R.id.recentMeg3).text
        findViewById<TextView>(R.id.recentMeg3).text = findViewById<TextView>(R.id.recentMeg2).text
        findViewById<TextView>(R.id.recentMeg2).text = findViewById<TextView>(R.id.recentMeg1).text
        findViewById<TextView>(R.id.recentMeg1).text = msg
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}