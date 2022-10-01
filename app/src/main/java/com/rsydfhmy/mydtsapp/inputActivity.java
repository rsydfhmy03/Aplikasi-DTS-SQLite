package com.rsydfhmy.mydtsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class inputActivity extends AppCompatActivity {

    //inisiasi variable location
    Button btnLocation;
    TextView etLoc1, etLoc2, etLoc3, etLoc4, etLoc5;
    FusedLocationProviderClient fusedLocationProviderClient;
    //--end code--
    //image
    Button btnUpload;
    final int REQUEST_CODE_GALLERY = 999;
    private ImageView foto;
    //end code--
    //Sqlite code--
    private EditText nama,alamat ,noHp  ;
    Button btnsubmit;
    //sqlitecode2
    private RadioButton MALE, FAMALE;
    //Variable Untuk Inisialisasi Database DBMahasiswa
//    private DBMahasiswa dbMahasiswa;
    public static DBMahasiswa dbMahasiswa;
    //Variable Untuk Menyimpan Input Dari Ueer
    private String setNama;
    private String setAlamat;
    private String setNoHp;
    private String setLokasi;
    private String setJenisKelamin;
    private byte[] setImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_input );

        //toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Form Pendaftaran");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //toolbar --end code--

        // SQlite database initialization & database & table create fuctions
//        db = new DBHelper(this,"Registrasi.sqlite",null,1);
//        db.queryData("CREATE TABLE IF NOT EXISTS Mahasiswa (ID INTEGER PRIMARY KEY AUTOINCREMENT, nama VARCHAR, alamat TEXT, noHp TEXT,Jenis_kelamin STRING ,Lokasi TEXT ,foto BLOB)");
        //test1
        btnsubmit = findViewById(R.id.btnsubmit);
        nama =findViewById( R.id.inputnama );
        alamat=findViewById( R.id.inputAlamat );
        noHp=findViewById( R.id.noHp );
//        JkL=findViewById( R.id.radio_laki );
        MALE = findViewById(R.id.radio_laki);
        FAMALE = findViewById(R.id.radio_pi);
        //Inisialisasi dan Mendapatkan Konteks dari DBMahasiswa
        dbMahasiswa = new DBMahasiswa(getBaseContext());

        //G-play Service --start Code--
        //Assign variable
        btnLocation = findViewById(R.id.btnLoc);
        etLoc1 = findViewById(R.id.etLoc1);
        etLoc2 = findViewById(R.id.etLoc2);
        etLoc3 = findViewById(R.id.etLoc3);
        etLoc4 = findViewById(R.id.etLoc4);
        etLoc5 = findViewById(R.id.etLoc5);
        //inisiasi fused location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(inputActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permissionn granted
                    getMyLocation();
                } else {
                    //when permissionn denided
                    ActivityCompat.requestPermissions(inputActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        //btn-- G play service

        // object initialize image upload
        btnUpload = findViewById(R.id.btnupload);
        foto = findViewById(R.id.imagepst);
        // Imagechose button action
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Request Permission
                ActivityCompat.requestPermissions(inputActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

            }
        });
        // Add Food button Action
//        btnsubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try
//                {
//                    db.insertdata(
//                            nama.getText().toString().trim(),
//                            alamat.getText().toString().trim(),
//                            noHp.getText().toString().trim(),
//                            JkL.getText().toString().trim(),
//                            imageViewToByte(foto),
//                            etLoc5.getText().toString().trim()
//                    );
//                    // after execute before insert data method then show a messege to notify user & set all empty
//                    Toast.makeText(inputActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
//                    nama.setText("");
//                    alamat.setText("");
//                    noHp.setText(" ");
//                    JkL.setText(" ");
//                    etLoc5.setText(" ");
//                    foto.setImageResource(R.drawable.ic_image_upload);
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                saveData();
                Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                clearData();
            }
        });

    }

    private void clearData() {;
        nama.setText("");
        alamat.setText("");
        noHp.setText("");
    }

    private void saveData() {
        //Mendapatkan Repository dengan Mode Menulis
        SQLiteDatabase create = dbMahasiswa.getWritableDatabase();

        //Membuat Map Baru, Yang Berisi Nama Kolom dan Data Yang Ingin Dimasukan
        ContentValues values = new ContentValues();
        values.put(DBMahasiswa.MyColumns.Nama, setNama);
        values.put(DBMahasiswa.MyColumns.Alamat, setAlamat);
        values.put(DBMahasiswa.MyColumns.NoHp, setNoHp );
        values.put(DBMahasiswa.MyColumns.JenisKelamin, setJenisKelamin);
        values.put(DBMahasiswa.MyColumns.Lokasi, setLokasi);
        values.put(DBMahasiswa.MyColumns.Foto, setImage);

        //Menambahkan Baris Baru, Berupa Data Yang Sudah Diinputkan pada Kolom didalam Database
        create.insert(DBMahasiswa.MyColumns.NamaTabel, null, values);
    }

    private void setData() {
        setNama = nama.getText().toString();
        if(MALE.isChecked()){
            setJenisKelamin = MALE.getText().toString();
        }else if (FAMALE.isChecked()){
            setJenisKelamin = FAMALE.getText().toString();
        }
        setLokasi = etLoc5.getText().toString();
        setAlamat = alamat.getText().toString();
        setNoHp = noHp.getText().toString();
        setImage = imageViewToByte(foto);
    }

    //method get location
    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //inisiasi geoCoder
                        Geocoder geocoder = new Geocoder(inputActivity.this,
                                Locale.getDefault());
                        //inisiasi address List
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);

                        //set Latitude on text view
                        etLoc1.setText( Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        //set Longtitude
                        etLoc2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longtitude :</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //set Country Name
                        etLoc3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name :</b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));
                        //set Locality
                        etLoc4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality :</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));
                        //set Address
                        etLoc5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address :</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //end method get location

    // caller method in save food insert image
    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    // on Request Permission overide method here for check storage permission added or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(inputActivity.this, "You dont have permission to access file storage", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // on Activity Result override method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                // set our ui image view from storage choose image
                foto.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //end code--image
}