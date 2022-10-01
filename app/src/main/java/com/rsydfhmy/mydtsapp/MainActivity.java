package com.rsydfhmy.mydtsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Button btninput = (Button) findViewById(R.id.btninput);
        btninput.setOnClickListener((View)-> {
            Intent intent = new Intent(MainActivity.this, inputActivity.class);
            startActivity(intent);
        });

        Button btnhasil = (Button) findViewById(R.id.btnhasil);
        btnhasil.setOnClickListener((View)-> {
            Intent intent = new Intent(MainActivity.this, RecyclerViewAdapter.class);
            startActivity(intent);
        });

        Button btnabout = (Button) findViewById(R.id.btnabout);
        btnabout.setOnClickListener((View)-> {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        });
    }
}