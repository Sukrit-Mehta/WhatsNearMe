/*
package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*
*/
/*

*//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*
*//*

*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.commit();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                  //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        fetchLocation();

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
     }
     else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    editor.putFloat("lat",(float)latitude);
                    editor.putFloat("long",(float)longitude);
                    editor.commit();
                    if(location==null)
                    {
                        latitude=sPref.getFloat("lat",0.0f);
                        longitude=sPref.getFloat("long",0.0f);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
              return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
*//*
*/
/*


//Thhodi si dikkat
*//*

*/
/*

package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*
*/
/*

*//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*
*//*

*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.spt();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }
        else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
        lis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                editor.putFloat("lat",(float)latitude);
                editor.putFloat("long",(float)longitude);
                editor.commit();
                if(location==null)
                {
                    latitude=sPref.getFloat("lat",0.0f);
                    longitude=sPref.getFloat("long",0.0f);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*//*
*/
/*


package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationManager llm;
    LocationListener ls;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //return;
                    }
                    gridView = findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    Log.d(TAG, "onRequestPermissionsResult: ");
                    locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
                    if(locationManager.isProviderEnabled(==false)
                    {
                        fetchLocation();
                    }
                    else
                    {
                        func();
                    }
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void func()
    {
        llm= (LocationManager) getSystemService(LOCATION_SERVICE);
        ls=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude=location.getLatitude();
                long
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestPermissionsFunc();

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }

        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location!=null)
            Log.d(TAG, "onCreate: ss"+location.getLatitude());

        else {
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude() + "," + location.getLongitude());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, lis);
        }
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*//*

*/
/*
package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.commit();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                  //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        fetchLocation();

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
     }
     else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    editor.putFloat("lat",(float)latitude);
                    editor.putFloat("long",(float)longitude);
                    editor.commit();
                    if(location==null)
                    {
                        latitude=sPref.getFloat("lat",0.0f);
                        longitude=sPref.getFloat("long",0.0f);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
              return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
*//*


//Thhodi si dikkat
*/
/*

package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.spt();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }
        else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
        lis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                editor.putFloat("lat",(float)latitude);
                editor.putFloat("long",(float)longitude);
                editor.commit();
                if(location==null)
                {
                    latitude=sPref.getFloat("lat",0.0f);
                    longitude=sPref.getFloat("long",0.0f);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*//*


package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    */
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestPermissionsFunc();

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }

        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location!=null)
            Log.d(TAG, "onCreate: ss"+location.getLatitude());

        else {
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude() + "," + location.getLongitude());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, lis);
        }
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*/
/*
package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.commit();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                  //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        fetchLocation();

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
     }
     else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    editor.putFloat("lat",(float)latitude);
                    editor.putFloat("long",(float)longitude);
                    editor.commit();
                    if(location==null)
                    {
                        latitude=sPref.getFloat("lat",0.0f);
                        longitude=sPref.getFloat("long",0.0f);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
              return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
*//*


//Thhodi si dikkat
*/
/*

package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    *//*

*/
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*
*/
/*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.spt();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }
        else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
        lis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                editor.putFloat("lat",(float)latitude);
                editor.putFloat("long",(float)longitude);
                editor.commit();
                if(location==null)
                {
                    latitude=sPref.getFloat("lat",0.0f);
                    longitude=sPref.getFloat("long",0.0f);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*//*


package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationManager llm;
    LocationListener ls;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    */
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //return;
                    }
                    gridView = findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    Log.d(TAG, "onRequestPermissionsResult: ");
                    locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
                    if(locationManager.isProviderEnabled(==false)
                    {
                        fetchLocation();
                    }
                    else
                    {
                        func();
                    }
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void func()
    {
        llm= (LocationManager) getSystemService(LOCATION_SERVICE);
        ls=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude=location.getLatitude();
                long
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestPermissionsFunc();

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }

        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location!=null)
            Log.d(TAG, "onCreate: ss"+location.getLatitude());

        else {
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude() + "," + location.getLongitude());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, lis);
        }
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*/
/*
package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    */
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.commit();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                  //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        fetchLocation();

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
     }
     else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
            lis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
                    editor.putFloat("lat",(float)latitude);
                    editor.putFloat("long",(float)longitude);
                    editor.commit();
                    if(location==null)
                    {
                        latitude=sPref.getFloat("lat",0.0f);
                        longitude=sPref.getFloat("long",0.0f);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
              return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
*/

//Thhodi si dikkat
/*

package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";

    */
/*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*//*


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lis = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            editor.putFloat("lat",(float)latitude);
                            editor.putFloat("long",(float)longitude);
                            editor.spt();
                            if(location==null)
                            {
                                latitude=sPref.getFloat("lat",0.0f);
                                longitude=sPref.getFloat("long",0.0f);
                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };
                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sPref=getSharedPreferences(MYPREF,MODE_PRIVATE);
        editor=sPref.edit();

        requestPermissionsFunc();

        Log.d(TAG, "onCreate: "+sPref.getFloat("lat",0.1f));

        //Toolbar
        mToolbar=findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView=findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location11!=null) {
            latitude = location11.getLatitude();
            longitude = location11.getLongitude();
            editor.putFloat("lat",(float)latitude);
            editor.putFloat("long",(float)longitude);
            editor.commit();
            Log.d(TAG, "onCreate: L1: "+latitude+","+longitude);
        }
        else {
            latitude=sPref.getFloat("lat",0.1f);
            longitude=sPref.getFloat("long",0.1f);
        }
        lis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: " + location.getLatitude()+","+location.getLongitude());
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                editor.putFloat("lat",(float)latitude);
                editor.putFloat("long",(float)longitude);
                editor.commit();
                if(location==null)
                {
                    latitude=sPref.getFloat("lat",0.0f);
                    longitude=sPref.getFloat("long",0.0f);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10, lis);
        progressDialog = new ProgressDialog(HomeActivity.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        fetchLocation();

        imageAdapter = new ImageAdapter(HomeActivity.this);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
    }

    private void requestPermissionsFunc()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        }
    }


    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 100, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000, 100, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}*/

package sukrit.example.com.nearme.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import sukrit.example.com.nearme.Adapters.ImageAdapter;
import sukrit.example.com.nearme.R;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    LocationManager manager;
    GridView gridView;
    ImageAdapter imageAdapter;

    LocationListener lis;

    LocationManager locationManager;
    android.location.LocationListener locationListener;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    boolean network_enabled = false;
    boolean locationFound = false;

    static double latitude;
    static double longitude;
    String latStr = "", longStr = "";

    public static final Integer REQUEST_CHECK_SETTINGS = 888;
    public static final String TAG = "locationsettings";
    TextView textView;

    Toolbar mToolbar;
    SearchView searchView;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    public static final String MYPREF = "MYPREF";
    boolean on=false;
    LocationListener lisNew;

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    LocationManager locationManager1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener1 = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "onLocationChangedPerm: " + location.getLatitude()+","+location.getLongitude());
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    };

                    Log.d(TAG, "onRequestPermissionsResult: "+locationManager1);
                        locationManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener1);
                        fetchLocation();

                    Log.d(TAG, "onRequestPermissionsResult: "+locationManager1.
                            getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude()+"");
                    latitude=locationManager1.
                            getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                    longitude=locationManager1.
                            getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

                    gridView=findViewById(R.id.gridView);
                    gridView.setAdapter(new ImageAdapter(HomeActivity.this));

                    fetchLocation();
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                            1000, 100, lis);
                    //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission Denied
                    Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");

        textView = findViewById(R.id.text);

        locationFound = false;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);

        } else {
            //Toolbar

            on=manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "onCreate: ON VALUE"+on);

                lisNew = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                        Log.d(TAG, "onLocationChanged: ON TRUE"+latitude+","+longitude);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,lisNew);

            /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }*/
            Location location11 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "onCreate: ELSE");
            if (location11 != null) {
                latitude = location11.getLatitude();
                longitude = location11.getLongitude();
                Log.d(TAG, "onCreate: L1: " + latitude + "," + longitude);
            }

            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null)
                Log.d(TAG, "onCreate: ss" + location.getLatitude());

            else {
                lis = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d(TAG, "onLocationChanged: " + location.getLatitude() + "," + location.getLongitude());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };
                manager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 0, 0, lis);
            }
            progressDialog = new ProgressDialog(HomeActivity.this);

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();


            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationProviderApi = LocationServices.FusedLocationApi;
            fetchLocation();

            imageAdapter = new ImageAdapter(HomeActivity.this);

            gridView = findViewById(R.id.gridView);
            gridView.setAdapter(imageAdapter);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
//        et1.setText(String.valueOf(location.getLatitude()));
//        et2.setText(String.valueOf(location.getLongitude()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        turnGPSOff();
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void fetchLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {

        }
        if (!network_enabled) {

            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(10000 / 2);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            Log.i(TAG, "All location settings are satisfied.");

                            progressDialog.dismiss();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
//                                progressDialog.dismiss();
                                status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i(TAG, "PendingIntent unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            Toast.makeText(HomeActivity.this, "Turn on Location", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            break;
                    }
                }
            });

            if (Build.VERSION.SDK_INT < 23) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, lis);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Ask for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    Log.i("TAG", "Permission given");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, lis);
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(Places.GEO_DATA_API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    mGoogleApiClient.connect();

                    if (mGoogleApiClient.isConnected()) {
                        //  startLocationUpdates();
                        Log.d(TAG, "Location update resumed .....................");
                    }
                }
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}