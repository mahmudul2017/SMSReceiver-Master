package com.example.smsreceiver_master.broadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.smsreceiver_master.MainActivity
import com.example.smsreceiver_master.broadCastReceiver.SmsReceiver.*

open class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras

        if (extras != null) {
            val sms = extras.get("pdus") as Array<Any>

            for (i in sms.indices) {
                val format = extras.getString("format")

                var smsMessage = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                } else {
                    SmsMessage.createFromPdu(sms[i] as ByteArray)
                }

                val phoneNumber = smsMessage.originatingAddress
                val messageText = smsMessage.messageBody.toString()

                val intent = Intent("broadCastName")
                intent.putExtra("phone", phoneNumber)
                context?.sendBroadcast(intent)
                
                Log.d("sms","Sms Details: Phone Number: $phoneNumber and Message Body: $messageText")
            }
        }
    }

    interface sendSmsInfo {
        fun onSmsInfo(phoneNumber: String, messsageBody: String)
    }
}