package com.example.blockchain_app;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WipeDeviceActivity extends AppCompatActivity {

    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    Button btnConfirmWipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wipe_device);

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, MyDeviceAdminReceiver.class);

        Button btnConfirmWipe = findViewById(R.id.btnConfirmWipe);
        btnConfirmWipe.setOnClickListener(v -> {
            devicePolicyManager.wipeData(0);  // WARNING: Erases all data
            finish();
        });
    }
}
