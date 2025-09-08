package com.example.blockchain_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class RegisterDeviceActivity extends AppCompatActivity {
    EditText imeiInput, ownerInput;
    Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_device);

        imeiInput = findViewById(R.id.inputImei);
        ownerInput = findViewById(R.id.inputOwner);
        registerButton = findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(v -> {
            String imei = imeiInput.getText().toString().trim();
            String owner = ownerInput.getText().toString().trim();

            if (!imei.isEmpty() && !owner.isEmpty()) {
                String hashedImei = HashUtil.sha256(imei);
                Device device = new Device(hashedImei, owner);
                Blockchain.addDevice(device);
                Toast.makeText(this, "Device registered!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
