package com.example.blockchain_app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceListActivity extends AppCompatActivity {
    ListView deviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        deviceListView = findViewById(R.id.deviceList);
        List<Device> devices = Blockchain.getAllDevices();

        List<String> displayList = new ArrayList<>();
        for (Device d : devices) {
            displayList.add("IMEI: " + d.getImei().substring(0, 10) + "... | Owner: " + d.getOwnerName() + (d.isLost() ? " [LOST]" : ""));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        deviceListView.setAdapter(adapter);
    }
}
