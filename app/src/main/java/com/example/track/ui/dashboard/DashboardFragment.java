package com.example.track.ui.dashboard;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.track.DataBaseHelper;
import com.example.track.MainActivity;
import com.example.track.R;
import com.example.track.Track;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private DashboardViewModel dashboardViewModel;
    private MapView mapView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        MainActivity mainActivity = (MainActivity) getActivity();
        final Location currentLocation = mainActivity.getUserLocation();
//        if(location!=null){
//            Toast.makeText(getContext(), "null location!", Toast.LENGTH_SHORT).show();
//        }

        mapView = root.findViewById(R.id.mapView);
        final ArrayList<Track> tracks = fetchLocalData();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 10f));
                googleMap.addMarker(new MarkerOptions().
                        position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).
                        title("Your position").
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                handleTracks(tracks,googleMap);
            }
        });
        mapView.onCreate(savedInstanceState);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public ArrayList<Track> fetchLocalData() {
        DataBaseHelper db = new DataBaseHelper(getContext());
        return db.getAll();
    }
    public void handleTracks(ArrayList<Track> tracks,GoogleMap googleMap){
        Toast.makeText(getContext(),String.valueOf(tracks.size()), Toast.LENGTH_SHORT).show();
        for (Track t:tracks){
            googleMap.addMarker(new MarkerOptions().
                    position(new LatLng(t.getLatitude(), t.getLongitude())).
                    title(t.getName()));
        }
    }
}