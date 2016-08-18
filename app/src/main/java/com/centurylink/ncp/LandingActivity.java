package com.centurylink.ncp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.centurylink.ncp.adapter.LocationAdapter;
import com.centurylink.ncp.models.MarkerModel;
import com.centurylink.ncp.utils.PermissionUtils;
import com.centurylink.ncp.utils.RecyclerViewOnItemTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMarkerClickListener{

    DrawerLayout  drawerLayout;
    private Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private RecyclerView makers_list;
    private List<Marker> marker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        initNavigationDrawer();
        makers_list = (RecyclerView)findViewById(R.id.makers_list);
        makers_list.addOnItemTouchListener(new RecyclerViewOnItemTouchListener(this, new RecyclerViewOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                changeMarkerPosition(position);
            }
        }));

        SupportMapFragment smf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.g_map);
        smf.getMapAsync(this);
    }
    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.add:
                        startActivity(new Intent(LandingActivity.this, AddVehicleActivity.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.park:
                        startActivity(new Intent(LandingActivity.this,FragmentIndicator.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.find:
                      //  startActivity(new Intent(LandingActivity.this,CircularfrgmentActivity.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        tv_email.setText("centurylink.com");
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

 actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMarkerClickListener(this);
        enableMyLocation();

        addMarkers();
        populateItems();

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    public void populateItems(){

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        makers_list.setLayoutManager(llm);

        LocationAdapter mlocationAdapter = new LocationAdapter(this,getAreasAround());
        makers_list.setAdapter(mlocationAdapter);

    }


    public List<MarkerModel> getAreasAround(){

        List<MarkerModel> all_marker = new ArrayList();

        MarkerModel markerModel = new MarkerModel();
        markerModel.setMain_text("Cessna Business Park");
        markerModel.setSub_text("Kaverappa Layout, Kadubeesanahalli, Kaverappa Layout, Kadubeesanahalli, Bengaluru, Karnataka 560103, India");
        markerModel.setDesc_text("12 km");
        markerModel.setLatitude(12.935381);
        markerModel.setLongitude(77.694114);
        all_marker.add(markerModel);

        markerModel = new MarkerModel();
        markerModel.setMain_text("Salarpuria Touchstone");
        markerModel.setSub_text("4th Floor, Varthur Hobli, Bengaluru, Karnataka 560103, India");
        markerModel.setDesc_text("13 km");
        markerModel.setLatitude(12.936395);
        markerModel.setLongitude(77.691406);
        all_marker.add(markerModel);

        markerModel = new MarkerModel();
        markerModel.setMain_text("New Horizon College of Engineering");
        markerModel.setSub_text("Outer Ring Road, Near Marathalli, Bellandur Main Road, Kaverappa Layout, Kadubeesanahalli, Bengaluru, Karnataka 560103, India");
        markerModel.setDesc_text("16 km");
        markerModel.setLatitude(12.933534);
        markerModel.setLongitude(77.691077);
        all_marker.add(markerModel);

        markerModel = new MarkerModel();
        markerModel.setMain_text("J.P. Morgan Chase");
        markerModel.setSub_text("Outer Ring Road, Kaverappa Layout, Kadubeesanahalli, Kadubeesanahalli, Bengaluru, Karnataka 560103, India");
        markerModel.setDesc_text("20 km");
        markerModel.setLatitude(12.942877);
        markerModel.setLongitude(77.696602);
        all_marker.add(markerModel);

        return  all_marker;
    }

    public void addMarkers(){

        List<MarkerModel> all_marker = getAreasAround();
        marker = new ArrayList<>();

        int numMarkers = all_marker.size();
        for (int i = 0; i < all_marker.size(); i++) {
            marker.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(all_marker.get(i).getLatitude(), all_marker.get(i).getLongitude()))
                    .title(all_marker.get(i).getMain_text())
                    .snippet(String.valueOf(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkers)))) ;
        }


        final View mapView = getSupportFragmentManager().findFragmentById(R.id.g_map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {

                    LatLngBounds.Builder bounds = new LatLngBounds.Builder();
                    for (int i = 0; i < marker.size(); i++) {
                        bounds.include(marker.get(i).getPosition());
                    }


                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),260));
                }
            });
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        LinearLayoutManager managre = (LinearLayoutManager) makers_list.getLayoutManager();
        managre.scrollToPosition(Integer.parseInt(marker.getSnippet()));

        return false;
    }

    public void changeMarkerPosition(int position){

        if(marker != null){
            marker.get(position).showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.get(position).getPosition()));
        }

    }
}
