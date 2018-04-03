package com.example.unsan.gpstracker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Unsan on 23/3/18.
 */

public class DestinationActivity extends AppCompatActivity {
    ImageButton takeButton;
    Button submit;
    int RC_PHOTO_PICKER = 123;
    ImageView imgview;
    Customer customer;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase fbd;
    DatabaseReference trackingReference;
    DatabaseReference destinationReference;
    DatabaseReference carReference;
    String strLat,strLng;
    String startAddress;

    private StorageReference storageReference;
    static Uri capturedImageUri = null;
    String mCurrentPhotoPath;
    Delivery delivery;
    String dateString;
    LocationManager lm;
    double longitude,latitude;
    String address;
    double lat,lng;
    String startTime;
    Uri downloadUrl;
    String node;
    ProgressBar pgbar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_layout);


        takeButton = (ImageButton) findViewById(R.id.imgbut);
        submit = (Button) findViewById(R.id.submit);
        imgview = (ImageView) findViewById(R.id.imgv);
        pgbar=(ProgressBar) findViewById(R.id.pgbar);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        pgbar.setVisibility(View.INVISIBLE);
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
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         longitude = location.getLongitude();
       latitude = location.getLatitude();




        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("destination_photo");

        fbd=FirebaseDatabase.getInstance();
        trackingReference=fbd.getReference("Tracking");
        destinationReference=fbd.getReference("delivery");
        carReference=fbd.getReference("Car");
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customerds");
        node=intent.getStringExtra("node");
       startTime= intent.getStringExtra("starttime");



        // String car=customer.carNumber.trim();

        Geocoder geocoder = new Geocoder(DestinationActivity.this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            startAddress = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addressList.get(0).getLocality();
            List<Address> addresses = geocoder.getFromLocationName(customer.address, 1);
            if ((addresses != null) && (addresses.size() > 0)) {
                Address fetchedAddress = addresses.get(0);
                lat = fetchedAddress.getLatitude();
                lng = fetchedAddress.getLongitude();
                Log.v("try-if", "ok great work");
            } else {
                Log.v("try-else", "something wrong");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("catch", "Could not get address....!");
        }

        strLat = String.valueOf(lat);
        Log.v("lat:", strLat);
        strLng = String.valueOf(lng);
        Log.v("lng:", strLng);



         Tracking tracking=new Tracking(customer.carNumber,latitude,longitude,lat,lng,customer.CustomerName);
         trackingReference.child(node).setValue(tracking).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Log.d("checkexception",e.getLocalizedMessage());
             }
         })
;
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbar.setVisibility(View.VISIBLE);

              //  capturedImageUri= FileProvider.getUriForFile(DestinationActivity.this, getApplicationContext().getPackageName() + ".com.example.unsan.gpstracker.GenericFileProvider", file);
                //capturedImageUri = Uri.fromFile(file);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // intent.setType("image/jpeg");
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri uri = FileProvider.getUriForFile(DestinationActivity.this, BuildConfig.APPLICATION_ID + ".provider",photoFile);
                        // Uri uri = Uri.fromFile(new File(path));


                        /*Uri photoURI = FileProvider.getUriForFile(DestinationActivity.this,
                                "com.example.unsan.gpstracker.GenericFileProvider",
                                photoFile);
                                */
                        capturedImageUri=Uri.fromFile(photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        startActivityForResult(takePictureIntent, RC_PHOTO_PICKER);
                    }
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long date=System.currentTimeMillis();
                SimpleDateFormat sd=new SimpleDateFormat("hh:mm");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateString = sdf.format(date);

                String desttime=sd.format(date);

               Delivery delivery=new Delivery(startTime,desttime,dateString,downloadUrl.toString(),customer.CustomerName,customer.address,startAddress,customer.carNumber,"name1");
               destinationReference.child(node).setValue(delivery);
               carReference.child(customer.carNumber).child(node).setValue("delivered");
               Intent intent =new Intent(DestinationActivity.this,MainPage.class);
               startActivity(intent);





            }
        });
    }





    public void onActivityResult(int requestcode, int resultcode, Intent data) {

        if (requestcode == RC_PHOTO_PICKER && resultcode == RESULT_OK) {

          //  final Uri SelectedImageUri = data.getData();
            Uri uri = capturedImageUri;
            Log.d("uripic",capturedImageUri.getLastPathSegment());
            Bitmap myImg = BitmapFactory.decodeFile(uri.getPath());
            imgview.setImageBitmap(myImg);


/*            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            imgview.setImageBitmap(imageBitmap);
            */

//            Log.d("checkim",SelectedImageUri.toString());
            StorageReference photoref = storageReference.child(capturedImageUri.getLastPathSegment());
            photoref.putFile(capturedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     downloadUrl = taskSnapshot.getDownloadUrl();
                     Log.d("checkurld",downloadUrl.toString());
                     pgbar.setVisibility(View.GONE);

                }
            });


           // encodeBitmapAndSaveToFirebase(imageBitmap);

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

   /* public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
       // Uri selectedImageUri = Uri.parse(imageEncoded);
        StorageReference photoref = storageReference.child(imageEncoded);
        photoref.putFile(imageEncoded).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

            }
        });
    }
    */
}

