package com.rsydfhmy.mydtsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TampilData extends AppCompatActivity {

    private RecyclerView recyclerView;
    public List<Konfigurasi> daftarMhs ;
    private LinearLayoutManager layoutManager;
    private Konfigurasi adapter;
    private TextView nodatatext;
//    private ArrayList<Konfigurasi> daftarMhs;
    // dialog widget
    private ImageView foto;
    private EditText nama;
    private EditText jenisKelamin;
    private EditText alamat;
    private EditText noHp;
    private EditText lokasi;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tampil_data );

        //toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Hasil Pendaftaran");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //toolbar --end code--
        nodatatext = findViewById(R.id.nodatatext);
        recyclerView = findViewById(R.id.foodrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        daftarMhs = new ArrayList<>();
        adapter = new Konfigurasi(this ,daftarMhs );
//        recyclerView.setAdapter(adapter);

        // check data exist or not in list
        if (daftarMhs.size() == 0)
        {
            nodatatext.setVisibility( View.VISIBLE);
        }

        //get foodlist data from sqlite database
        Cursor cursor = inputActivity.dbMahasiswa.getdata("SELECT * FROM Mahasiswa");
        daftarMhs.clear();
        while (cursor.moveToNext()) {
            // get data from column like index 0 = column1, 1 = column2
//            int id = cursor.getInt(0);
            String nama = cursor.getString(0);
            String jenisKelamin = cursor.getString(1);
            String alamat = cursor.getString( 2 );
            String noHp = cursor.getString( 3 );
            String Lokasi = cursor.getString( 4 );
            byte[] foto = cursor.getBlob(5);

            // set received data to list throw model class
            daftarMhs.add(new Konfigurasi(nama ,jenisKelamin, alamat , noHp , Lokasi, foto));
            nodatatext.setVisibility(View.GONE);
        }
//        adapter.notifyDataSetChanged();

//        adapter.setOnItemClickListener(new RequestHandler.OnItemClickListener() {
//            @Override
//            public void onItemClick(final int position) {
//
//                final CharSequence[] items = {"Lihat Data", "Update Data", "Delete Data"};
//                AlertDialog.Builder dialog = new AlertDialog.Builder(TampilData.this);
//                dialog.setTitle("Chosse an action");
//                dialog.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        if (which == 0) {
//                            Intent intent = new Intent(TampilData.this,About.class);
//                            startActivity(intent);
//
//                        }  else if (which == 1) {
//                            //GET POSITION OF ROW
//                            Cursor c = MainActivity.dbMahasiswa.getdata("SELECT id FROM Mahasiswa");
//                            ArrayList<Integer> arrID = new ArrayList<>();
//                            while (c.moveToNext()) {
//                                arrID.add(c.getInt(0));
//                            }
//                            // Show dialog updata here
//                            showDialogUpdate(Daftar.this, arrID.get(position));
//
//                        } else {
//
//                            //GET POSITION OF ROW
//                            Cursor c = MainActivity.sQliteHelper.getdata("SELECT id FROM Mahasiswa");
//                            ArrayList<Integer> arrID = new ArrayList<>();
//                            while (c.moveToNext()) {
//                                arrID.add(c.getInt(0));
//                            }
//                            // delete operation dialog method call
//                            showDialogDelete(arrID.get(position));
//                        }
//                    }
//                });
//                dialog.show();
//            }
//        });

    }

    // show dialog for delete data
    //Activity Hapus
    private void showDialogDelete(final int id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TampilData.this);
        alertDialogBuilder.setTitle("Warning").setMessage("Are you sure want to delete this data?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // delete operation
                try
                {
                    inputActivity.dbMahasiswa.deleteData(id);
                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                // call fro refresh food list
                refreshData();

            }
        }).setNegativeButton(" Not Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        }).show();

    }


    // show dialog for update
//    private void showDialogUpdate(Activity activity, final int position) {
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.activity_edit);
//        dialog.setTitle("Update Data");
//        foto = dialog.findViewById(R.id.editfoto);
//        nama = dialog.findViewById(R.id.editnama);
//        nrp = dialog.findViewById(R.id.editnrp);
//        btnEdit = dialog.findViewById(R.id.tomboledit);
//
//        // set dialog width
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
//        // set dialog width
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
//        // set height & width to dialog for apply
//        dialog.getWindow().setLayout(width, height);
//        dialog.show();
//
//        // dialog image click operation
//        foto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Request Permission
//                ActivityCompat.requestPermissions(Daftar.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 888);
//
//            }
//        });
//
//        // update button click oepration in dialog
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    MainActivity.sQliteHelper.updateData(nama.getText().toString().trim(), nrp.getText().toString().trim(), MainActivity.imageViewToByte(foto), position);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
//                // call fro refresh food list
//                refreshData();
//
//            }
//        });
//
//    }

    // this method is fro when update data & dialog close it will refresh recyclerview
    private void refreshData() {
        //get refreshData data from sqlite database
        Cursor cursor = inputActivity.dbMahasiswa.getdata("SELECT * FROM Mahasiswa");
        daftarMhs.clear();
        while (cursor.moveToNext()) {
            // get data from column like index 0 = column1, 1 = column2
//            int id = cursor.getInt(0);
//            String nama = cursor.getString(1);
//            String nrp = cursor.getString(2);
//            byte[] foto = cursor.getBlob(3);
//
//            // set received data to list throw model class
//            daftarMhs.add(new Konfigurasi(id, nama, nrp, foto));
            String nama = cursor.getString(0);
            String jenisKelamin = cursor.getString(1);
            String alamat = cursor.getString( 2 );
            String noHp = cursor.getString( 3 );
            String Lokasi = cursor.getString( 4 );
            byte[] foto = cursor.getBlob(5);

            // set received data to list throw model class
            daftarMhs.add(new Konfigurasi(nama ,jenisKelamin, alamat , noHp , Lokasi, foto));
            nodatatext.setVisibility(View.GONE);
        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(TampilData.this, "You dont have permission to access file storage", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // on Activity Result override method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                // set our ui image view from storage choose image
                foto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}

