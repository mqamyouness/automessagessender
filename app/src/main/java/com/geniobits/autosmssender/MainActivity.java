package com.geniobits.autosmssender;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private EditText txt_message;
    private EditText txt_number;
    private EditText txt_count;
    private Button btn_manual;
    private Button btn_sms;
    private Button btn_whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_message = findViewById(R.id.txt_message);
        txt_number = findViewById(R.id.txt_mobile);
        txt_count  = findViewById(R.id.txt_count);

        btn_manual = findViewById(R.id.btn_manual);
        btn_sms = findViewById(R.id.btn_sms);
        btn_whatsapp = findViewById(R.id.btn_whatsapp);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i=0; i < Integer.parseInt(txt_count.getText().toString()); i++) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(txt_number.getText().toString(), null, txt_message.getText().toString(), null, null);
                        Toast.makeText(MainActivity.this, "SMS Sent : count"+(i+1), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "SMS sending failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSms = new Intent(Intent.ACTION_SEND);
                sendSms.setType("text/plain");
                sendSms.putExtra(Intent.EXTRA_TEXT,txt_message.getText().toString());
                startActivity(sendSms);
            }
        });

        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
