package com.weather.app;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, OnMapReadyCallback, CoordListener {

    private MainFragment weatherFragment;
    private Double longitude;
    private Double latitude;
    private GoogleMap gMap;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherFragment = new MainFragment();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.weather_container, weatherFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem itemSearch =  menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        customizeMap(googleMap, new LatLng(latitude, longitude));
    }

    @Override
    public void onLatLngRes(Double lat, Double lng) {
        longitude = lng;
        latitude = lat;
        if (gMap == null) {
            mapFragment.getMapAsync(this);
            return;
        }
        customizeMap(gMap, new LatLng(latitude, longitude));
    }

    private void customizeMap(GoogleMap map, LatLng latLng){
        map.addMarker(new MarkerOptions().position(latLng).title("Current city"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        weatherFragment.search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
