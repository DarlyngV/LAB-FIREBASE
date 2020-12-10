package com.example.proyecto_final.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import java.util.Date;

public class MyLocationUpdateReceiver extends BroadcastReceiver {
    private double longitud;
    private double latitud;
    private EntrenamientoFragment n= new EntrenamientoFragment();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Prueba3", "onReceive: ");
 /*     if(intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)){
          String locationChanged= LocationManager.KEY_LOCATION_CHANGED;
          Location location= (Location) intent.getExtras().get(locationChanged);
          double latitud= location.getLatitude();
          double longitud= location.getLongitude();
          Log.d("Prueba", "onReceive: "+latitud+longitud);

      }
      
*/
            if (LocationResult.hasResult(intent)) {
                LocationResult locationResult = LocationResult.extractResult(intent);
                for (Location location : locationResult.getLocations()) {
                   latitud= location.getLatitude();
                    longitud= location.getLongitude();
                    Log.d("Prueba", "onReceive: "+latitud+longitud);
                    n.actualizar(latitud,longitud);
                    Date now = new Date();
                    n.homeViewModel.RecorridoTotal(12,14,now);

                }
            }

    }
    /*
@Override
public void onReceive(Context context, Intent intent) {
    Object LocationResult;
    if (LocationResult.hasResult(intent)) {
        LocationResult locationResult = LocationResult.extractResult(intent);
        for (Location location : locationResult.getLocations()) {

        }
    }
}

 */



}
