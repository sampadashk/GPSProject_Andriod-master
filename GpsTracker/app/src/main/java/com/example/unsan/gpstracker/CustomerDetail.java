package com.example.unsan.gpstracker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

/**
 * Created by Unsan on 23/3/18.
 */

public class CustomerDetail extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private FusedLocationProviderClient mFusedLocationClient;
    TextView name, phone, address, journeytxt;
    final int REQUEST_CHECK_SETTINGS = 125;
    Button start, reached;
    Customer c;
    String timestart, date;
    FirebaseDatabase fbdr;
    String mAddressOutput;
    String node;
    DatabaseReference startReference;
    double longitude, latitude;
    String startAddress;
    LocationManager lm;
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mResultReceiver = new AddressResultReceiver(null);


        fbdr = FirebaseDatabase.getInstance();
        startReference = fbdr.getReference("Started");
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        start = (Button) findViewById(R.id.start);
        reached = (Button) findViewById(R.id.reached);
        journeytxt = (TextView) findViewById(R.id.journryst);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Intent intent = getIntent();
        c = (Customer) intent.getSerializableExtra("customer");
        name.setText(c.getCustomerName());
        phone.setText(c.getPhone());
        address.setText(c.getAddress());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            Log.d("checklatlong", longitude + " " + latitude);

                            mLastLocation = location;
                            startIntentService();
                         /*   Geocoder geocoder = new Geocoder(CustomerDetail.this, Locale.getDefault());
                            try {
                                Log.d("herec",latitude+" "+longitude);
                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                Log.d("checkss",addressList.size()+" ");
                                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                if(addressList.size()>0) {
                                    startAddress = addressList.get(0).getAddressLine(0);
                                    Log.d("getlocation", startAddress);
                                }
                            }
                            catch(IOException e)
                            {
                                e.printStackTrace();
                            }
                            //startIntentService();
                            // Logic to handle location object
                        }
                        */

                        }

                    }
                });
     /*   Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        */


      //  createLocationRequest();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat simpleD = new SimpleDateFormat("dd/MM/yyyy");
                date = simpleD.format(time);


                timestart = simpleDateFormat.format(time);
                String car = c.carNumber.replaceAll("\\s", "");
                node = car + time;
                start.setText("Trip Started");
                start.setEnabled(false);
                StartJourney startJourney = new StartJourney(c.carNumber, date, timestart, startAddress, c.address, false);
                startReference.child(node).setValue(startJourney);
                journeytxt.setVisibility(View.VISIBLE);
                journeytxt.setText("Journey started at: " + timestart);
                reached.setVisibility(View.VISIBLE);
            }
        });
        reached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDetail.this, DestinationActivity.class);
                intent.putExtra("customerds", c);
                intent.putExtra("node", node);
                intent.putExtra("starttime",timestart);

                startActivity(intent);
            }
        });
       /*
      start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              long timevalue= System.currentTimeMillis();
               Intent intent=new Intent(CustomerDetail.this,DestinationActivity.class);
               intent.putExtra("customerds",c);

               startActivity(intent);



           }
       });
       */

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
    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(CustomerDetail.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CHECK_SETTINGS:
            {
                if(resultCode>0)
                {

                }
            }
        }
    }

  /*  protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }
    */
   class  AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            if (mAddressOutput == null) {
                mAddressOutput = "";
            }


            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(CustomerDetail.this,"address foundc"+mAddressOutput,Toast.LENGTH_SHORT).show();
                startAddress=mAddressOutput;
            }

        }
    }

}
