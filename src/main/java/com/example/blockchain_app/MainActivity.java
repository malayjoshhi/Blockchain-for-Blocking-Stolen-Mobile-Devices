package com.example.blockchain_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.web3j.protocol.core.Response;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    Button btnGoRegister, btnGoDeviceList, btnGoMarkLost, btnGoCheckStatus, btnAdminPanel;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            btnGoRegister = findViewById(R.id.btnRegisterDevice);
            btnGoDeviceList = findViewById(R.id.btnGoDeviceList);
            btnGoMarkLost = findViewById(R.id.btnMarkStolen);
            btnGoCheckStatus = findViewById(R.id.btnCheckStatus);
        btnAdminPanel = findViewById(R.id.btnAdminPanel);


            btnGoRegister.setOnClickListener(v ->
                    startActivity(new Intent(this, RegisterDeviceActivity.class)));

            btnGoDeviceList.setOnClickListener(v ->
                    startActivity(new Intent(this, DeviceListActivity.class)));

            btnGoMarkLost.setOnClickListener(v ->
                    startActivity(new Intent(this, MarkDeviceLostActivity.class)));

            btnGoCheckStatus.setOnClickListener(v -> {
                Intent intent = new Intent(this, CheckDeviceStatusActivity.class);
                startActivity(intent);
            });

        btnGoCheckStatus.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckDeviceStatusActivity.class);
            startActivity(intent);
        });


        btnAdminPanel.setOnClickListener(v ->
                startActivity(new Intent(this, AdminPanelActivity.class)));





    }


}
