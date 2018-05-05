package ua.in.easybike.home;


import android.app.Dialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{



    //case для підключення AlertDialog
    private final int IDD_LIST_LANGUAGE = 1;
    private final int IDD_LIST_UnitsOfLengths = 2;
    private final int IDD_LIST_SocialNetworks = 3;
    private final int IDD_LIST_FavourireRoute = 4;
    private final int IDD_LIST_FavouritePlace = 5;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Maps);
        mapFragment.getMapAsync(this);



    }



    @Override
    public void onMapReady(GoogleMap mMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e("MainActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MainActivity", "Can't find style. Error: ", e);
        }

        GoogleMap googleMap;

        // Locations
        GPSTracker mGPS = new GPSTracker(this);
        LatLng location = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
        mMap.addMarker(new MarkerOptions().position(location)
                .title("Your location")
                .icon(BitmapDescriptorFactory.fromResource(R.raw.marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    //Праве меню

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            new SimpleSearchDialogCompat(MainActivity.this, "Search ....", "Choose a streets", null,
                    initData(), new SearchResultListener<Searchable>() {
                @Override
                public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                    Toast.makeText(MainActivity.this, "" + searchable.getTitle(), Toast.LENGTH_LONG).show();
                    baseSearchDialogCompat.dismiss();
                }
            }).show();
        } else if (id == R.id.action_language) {
            //Begin-------------------------------------->
            showDialog(IDD_LIST_LANGUAGE);

            //End---------------------------------------->

        } else if (id == R.id.action_location) {



        } else if (id == R.id.action_unitsOfLenght){

            showDialog(IDD_LIST_UnitsOfLengths);

        } else if (id == R.id.action_socialNetworks){
            showDialog(IDD_LIST_SocialNetworks);
        }

        return super.onOptionsItemSelected(item);
    }


    private ArrayList<Search> initData() {
        ArrayList<Search> items = new ArrayList<>();
        items.add(new Search("Bohdanivsʹka"));
        items.add(new Search("Valova"));
        items.add(new Search("Horodotsʹka"));
        items.add(new Search("Zamkova"));


        return items;

    }



    //Ліве меню


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {


        } else if (id == R.id.nav_work) {


        } else if (id == R.id.nav_favouritePlace) {
            //AlertDialog
            showDialog(IDD_LIST_FavouritePlace);

        } else if (id == R.id.nav_favouriteRoute) {
            //AlertDialog
            showDialog(IDD_LIST_FavourireRoute);
        }
     else if (id == R.id.about_developers) {
            Intent intent = new Intent(MainActivity.this, AboutDevelopers.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    // On Create Dialog

    @Override
    protected  Dialog onCreateDialog(int id) {
        switch (id) {
            case IDD_LIST_LANGUAGE:

                final String[] languages = {"English", "Ukrainian"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose language");
                builder.setIcon(R.drawable.language);

                builder.setItems(languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                "You choose: " + languages[item],
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);
                 return builder.create();


            case IDD_LIST_UnitsOfLengths:

                final String[] lengths = {"Kilometers ", "Miles"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose lengths");
                builder.setIcon(R.drawable.lenghts);

                builder.setItems(lengths, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        //  TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                "You choose: " + lengths[item],
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);
                return builder.create();

            case IDD_LIST_SocialNetworks:

                final String[] network = {"Facebook", "Google +"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Network");
                builder.setIcon(R.drawable.cloud);

                builder.setItems(network, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        //  TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                "You choose: " + network[item],
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);
                return builder.create();

            case IDD_LIST_FavouritePlace:
                final String[] favouritePlace = {"Zamkova", "Valova"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Favourite place");
                builder.setIcon(R.drawable.ic_favorite_heart);
                builder.setNeutralButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new SimpleSearchDialogCompat(MainActivity.this, "Search ....", "Choose a streets", null,
                                initData(), new SearchResultListener<Searchable>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                                Toast.makeText(MainActivity.this, "" + searchable.getTitle(), Toast.LENGTH_LONG).show();
                                baseSearchDialogCompat.dismiss();
                            }
                        }).show();
                    }
                });

                builder.setItems(favouritePlace, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        //  TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                "You choose: " + favouritePlace [item],
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);
                return builder.create();


            case IDD_LIST_FavourireRoute:
                final String[] favouriteRoute = {"Zamkova - Valova", "Horodot'ska - Bohdanivs'ka"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Favourite route");
                builder.setIcon(R.drawable.ic_directions_bike);
                builder.setNeutralButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new SimpleSearchDialogCompat(MainActivity.this, "Search ....", "Choose a streets", null,
                                initData(), new SearchResultListener<Searchable>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                                Toast.makeText(MainActivity.this, "" + searchable.getTitle(), Toast.LENGTH_LONG).show();
                                baseSearchDialogCompat.dismiss();
                            }
                        }).show();
                    }
                });

                builder.setItems(favouriteRoute, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        //  TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                "You choose: " + favouriteRoute [item],
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);
                return builder.create();

            default:
                return null;
        }

    }
}



