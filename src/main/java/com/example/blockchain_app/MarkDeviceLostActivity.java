package com.example.blockchain_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkDeviceLostActivity extends AppCompatActivity {
    EditText inputImei;
    Button markLostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_lost);

        inputImei = findViewById(R.id.inputImeiLost);
        markLostBtn = findViewById(R.id.btnMarkLost);

        markLostBtn.setOnClickListener(v -> {
            String imei = inputImei.getText().toString().trim();
            String hashedImei = HashUtil.sha256(imei);

            Device device = Blockchain.findDeviceByImei(hashedImei);
            if (device != null) {
                device.setLost(true);
                Toast.makeText(this, "Device marked as lost!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Device not found!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
