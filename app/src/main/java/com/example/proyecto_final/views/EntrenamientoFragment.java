package com.example.proyecto_final.views;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.DAO.ConexionSQLiteHelper;
import com.example.proyecto_final.DAO.Utilidades;
import com.example.proyecto_final.DAO.daoModo;
import com.example.proyecto_final.R;
import com.example.proyecto_final.entities.Coordenada;
import com.example.proyecto_final.entities.Modo;
import com.example.proyecto_final.viewmodels.EntrenamientoViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class EntrenamientoFragment extends Fragment {
    private Spinner spinner;
    private Button iniciar;
    private Button terminar;
    private TextView kilometros;
    public  EntrenamientoViewModel homeViewModel;
    private static final long MIN_TIEMPO_ENTRE_UPDATES = 1000 * 5 * 1; // 1 minuto
    private static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = 1; // 1.5 metros
    private static daoModo dao;
    private  double longitud ;
    private double latitud;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(EntrenamientoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_entrenamiento, container, false);
        /*-----------------------------------------------------------------------------------------------------------------*/
        /*
         * Prepara la interfaz
         * */
        configView(root);
        /*-----------------------------------------------------------------------------------------------------------------*/


        /*ubicación*/
        /*

        LocationManager locationManager;

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LocationManager locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);
                final LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        Date now = new Date();


                        homeViewModel.RecorridoTotal(location.getLatitude(), location.getLongitude() ,now );

                        //kilometros.setText("" + location.getLatitude() + "" + location.getLongitude());
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    public void onProviderEnabled(String provider) {}

                    public void onProviderDisabled(String provider) {}
                };



               int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                // Register the listener with the Location Manager to receive location updates
               locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES,
                       MIN_CAMBIO_DISTANCIA_PARA_UPDATES, locationListener);

                terminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationManager.removeUpdates(locationListener);

                    }
                });

            }

        });

        int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) root.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }
            else{
                ActivityCompat.requestPermissions((Activity) root.getContext(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }

         */
        /* Ubicación 2 */

        /*-----------------------------------------------------------------------------------------------------------------*/
        /*PERMISOS*/
      /*  int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) root.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }
            else{
                ActivityCompat.requestPermissions((Activity) root.getContext(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }*/
        /*-----------------------------------------------------------------------------------------------------------------*/

  /*      Intent intent = new Intent(getActivity(), MyLocationUpdateReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        final LocationManager locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);
        int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIEMPO_ENTRE_UPDATES,MIN_CAMBIO_DISTANCIA_PARA_UPDATES,pendingIntent);
*/





        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Prueba2", "onReceive: ");
              //  int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
              /*  Intent intent = new Intent(getActivity(), MyLocationUpdateReceiver.class);
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(root.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                final LocationManager locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);
                int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIEMPO_ENTRE_UPDATES,MIN_CAMBIO_DISTANCIA_PARA_UPDATES,pendingIntent);
*/
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(root.getContext());
                LocationRequest request = new LocationRequest()
                        .setInterval(60000 * 2) // Update every 2 minutes.
                        .setPriority(LocationRequest.PRIORITY_NO_POWER);
                final int locationUpdateRC = 0;
                int flags = PendingIntent.FLAG_UPDATE_CURRENT;
                Intent intent = new Intent(root.getContext(), MyLocationUpdateReceiver.class);
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(root.getContext(), locationUpdateRC, intent, flags);

                if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(root.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }

                fusedLocationClient.requestLocationUpdates(request, pendingIntent);

            }


        });


        /*-----------------------------------------------------------------------------------------------------------------*/








/* ****************************************************************************************************** */
/*Observador*/

     final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String resultado) {
                kilometros.setText(resultado);
            }
        };

        homeViewModel.getText().observe(getViewLifecycleOwner(), observer);









        return root;
    }



public void actualizar(double latitud, double longitud){
        this.latitud=latitud;
        this.longitud=longitud;
    Log.d("Pruebasa", "onReceive: "+latitud+longitud);

}






    public void configView(View root){
        spinner= (Spinner) root.findViewById(R.id.modo);
        iniciar= (Button)root.findViewById(R.id.iniciar);
        terminar= (Button)root.findViewById(R.id.terminar);
        kilometros=(TextView)root.findViewById(R.id.kilometros) ;
        ComboBox(root);


    }
    public void ComboBox(View root){
        ArrayList<String> opciones = new ArrayList<String>();
       dao= new daoModo(getContext());
       dao.connect();
      for (Modo us:dao.selectModos()) {
           opciones.add(us.getNombre());
       }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);
    }

}