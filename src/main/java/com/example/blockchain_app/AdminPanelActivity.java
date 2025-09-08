package com.example.blockchain_app;




import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminPanelActivity extends AppCompatActivity {

    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, MyDeviceAdminReceiver.class);


        Button btnLockDevice = findViewById(R.id.btnLockDevice);
        Button btnWipeDevice = findViewById(R.id.btnWipeDevice);




        btnLockDevice.setOnClickListener(v -> lockDevice());
        btnWipeDevice.setOnClickListener(v -> wipeDevice());

    }

    private void blockTargetApp() {
        // Example: Launch system settings to manually disable apps
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        startActivity(intent);
    }


    private void lockDevice() {
        Intent intent = new Intent(AdminPanelActivity.this, LockDeviceActivity.class);
        startActivity(intent);
    }

    private void wipeDevice() {
        Intent intent = new Intent(AdminPanelActivity.this, WipeDeviceActivity.class);
        startActivity(intent);
    }

}

