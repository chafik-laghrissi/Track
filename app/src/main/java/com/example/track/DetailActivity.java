package com.example.track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

public class DetailActivity extends AppCompatActivity {
    private TextView name, altitude, longitude, latitude, date;
    private Button delete;
    private ImageView trackImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name = findViewById(R.id.trackNameDetail);
        altitude = findViewById(R.id.altitudeDetail);
        longitude = findViewById(R.id.longitudeDetail);
        latitude = findViewById(R.id.latitudeDetail);
        date = findViewById(R.id.date);
        trackImage = findViewById(R.id.pickedImageDetail);
        delete = findViewById(R.id.delete);
        final Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        date.setText(intent.getStringExtra("date"));
        final String imageStr = intent.getStringExtra("imageUri");
        longitude.setText(String.valueOf(intent.getDoubleExtra("longitude",0)));
        altitude.setText(String.valueOf(intent.getDoubleExtra("altitude",0)));
        latitude.setText(String.valueOf(intent.getDoubleExtra("latitude",0)));
        File toLoad=null;
        assert imageStr != null;
        if (!imageStr.equals("")) {
        toLoad = new File(this.getFilesDir(), imageStr);
            Toast.makeText(this, imageStr, Toast.LENGTH_SHORT).show();
            Glide.with(this)
                    .load(toLoad)
                    .into(trackImage);
        }
        final File finalToLoad = toLoad;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = intent.getIntExtra("id", -1);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(DetailActivity.this);
                boolean success = dataBaseHelper.deleteOneId(id);
                if(finalToLoad !=null)
                    finalToLoad.delete();
                Toast.makeText(DetailActivity.this, "removed successfully", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent1);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });
    }
}