package com.example.track;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class CreateTrack extends AppCompatActivity {
    private EditText longitude, latitude, altitude, name;
    private Button save, selector;
    static int SELECT_IMAGE_CODE = 1;
    private ImageView image;
    private Track track;
    private Uri imageUri = null;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateTrack.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_track);
        final Intent intent = getIntent();
        selector = findViewById(R.id.imagePicker);
        image = findViewById(R.id.pickedImage);
        final double[] longitudeValue = {intent.getDoubleExtra("longitude", 0)};
        longitude = findViewById(R.id.tvLongitude);
        longitude.setText(String.valueOf(longitudeValue[0]));
        final double[] latitudeValue = {intent.getDoubleExtra("latitude", 0)};
        latitude = findViewById(R.id.tvLatitude);
        latitude.setText(String.valueOf(latitudeValue[0]));
        final double[] altitudeValue = {intent.getDoubleExtra("altitude", 0)};
        altitude = findViewById(R.id.tvAltitude);
        altitude.setText(String.valueOf(altitudeValue[0]));
        name = findViewById(R.id.trackName);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String newPath = saveImage(imageUri);
                if (latitudeValue[0] != Double.valueOf(latitude.getText().toString())) {
                    latitudeValue[0] = Double.valueOf(latitude.getText().toString());
                }

                if (altitudeValue[0] != Double.valueOf(altitude.getText().toString())) {
                    altitudeValue[0] = Double.valueOf(altitude.getText().toString());
                }

                if (longitudeValue[0] != Double.valueOf(longitude.getText().toString())) {
                    longitudeValue[0] = Double.valueOf(longitude.getText().toString());
                }
                if (newPath == null)
                    track = new Track(-1, latitudeValue[0], altitudeValue[0], longitudeValue[0], nameValue);
                else
                    track = new Track(-1, latitudeValue[0], altitudeValue[0], longitudeValue[0], newPath, nameValue);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(CreateTrack.this);
                boolean succeed = dataBaseHelper.addOne(track);
                if (succeed) {
                    Toast.makeText(CreateTrack.this, "The track has been created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateTrack.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateTrack.this, "We couldn't create new track! please try again", Toast.LENGTH_SHORT).show();
                }

            }

        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                save.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {
                    save.setEnabled(true);
                } else {
                    save.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_CODE) {
            imageUri = data.getData();
            image.setImageURI(imageUri);

        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public String uniqueID() {
        return UUID.randomUUID().toString();
    }

    public String saveImage(Uri uri) {
        if (uri != null) {
            File file = new File(uri.getPath());
            FileOutputStream fos = null;
            try {
                String pathName = uniqueID() + ".png";
                fos = openFileOutput(pathName, MODE_PRIVATE);
                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] inputData = getBytes(iStream);
                fos.write(inputData);
                Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
                return pathName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else
            return null;
    }
}