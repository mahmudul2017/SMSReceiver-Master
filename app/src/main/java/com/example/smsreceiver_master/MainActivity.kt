package com.example.smsreceiver_master

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.smsreceiver_master.broadCastReceiver.SmsReceiver
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val requestReceiveSms: Int = 2

    var broadcastReceiver: SmsReceiver = object : SmsReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            /*val b = intent?.extras
            val message = b!!.getString("message")*/
            val phoneNo = intent?.extras?.getString("phone")
            tv_message.text = phoneNo
            Log.e("phone-M", "$phoneNo")

            Toast.makeText(this@MainActivity, "$phoneNo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS),
                requestReceiveSms
            )
        }

        registerReceiver(broadcastReceiver, IntentFilter("broadCastName"))
    }
       //tv_message.text = "Phone Number: $phoneNumber\n Message: $messsageBody"
}