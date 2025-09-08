package com.example.blockchain_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeviceApi {

    @POST("devices")
    Call<Void> registerDevice(@Body Device device);

    @GET("devices")
    Call<List<Device>> getDevices();

    @PUT("devices/{imeiHash}/lost")
    Call<Void> markDeviceLost(@Path("imeiHash") String imeiHash, @Body LostStatus status);

    @GET("devices/{imeiHash}")
    Call<Device> getDeviceByImei(@Path("imeiHash") String imeiHash);

    class LostStatus {
        boolean isLost;

        public LostStatus(boolean isLost) {
            this.isLost = isLost;
        }
    }
}
