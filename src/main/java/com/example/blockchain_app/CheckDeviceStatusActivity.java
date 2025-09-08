package com.example.blockchain_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckDeviceStatusActivity extends AppCompatActivity {
    private EditText inputImeiCheck;
    private TextView resultText;
    private Button btnCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_device_status);

        inputImeiCheck = findViewById(R.id.inputImeiCheck);
        resultText = findViewById(R.id.resultText);
        btnCheckStatus = findViewById(R.id.btnCheckStatus);

        btnCheckStatus.setOnClickListener(v -> {
            String imeiInput = inputImeiCheck.getText().toString().trim();

            if (imeiInput.isEmpty()) {
                resultText.setText("Please enter an IMEI number.");
                return;
            }

            String hashedImei = HashUtil.sha256(imeiInput);
            Device device = Blockchain.findDeviceByImei(hashedImei);

            if (device != null) {
                String status = device.isLost() ? "⚠️ This device is reported LOST." : "✅ This device is SAFE.";
                resultText.setText("Device Found\nOwner: " + device.getOwnerName() + "\nStatus: " + status);
            } else {
                resultText.setText("❌ Device not found in registry.");
            }
        });
    }
}
