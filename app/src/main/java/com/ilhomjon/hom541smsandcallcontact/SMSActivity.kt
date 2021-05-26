package com.ilhomjon.hom541smsandcallcontact

import Models.Contact
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.florent37.runtimepermission.kotlin.askPermission
import kotlinx.android.synthetic.main.activity_s_m_s.*

class SMSActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_m_s)

        val contact = intent.getSerializableExtra("key") as Contact
        txt_name_sms.text = contact.name
        txt_number_sms.text = contact.number

        btn_send.setOnClickListener {
            askPermission(Manifest.permission.SEND_SMS){
                //all permissions already granted or just granted

                val matn = edt_matn.text.toString()
                var obj = SmsManager.getDefault()
                obj.sendTextMessage(contact.number,
                    null,  matn,
                    null, null)
                Toast.makeText(this, "Send message", Toast.LENGTH_SHORT).show()

            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(this)
                        .setMessage("Ruxsat bermasangiz ilova ishlay olmaydi ruxsat bering...")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if(e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        }

        card_ortga.setOnClickListener {
            finish()
        }
    }
}