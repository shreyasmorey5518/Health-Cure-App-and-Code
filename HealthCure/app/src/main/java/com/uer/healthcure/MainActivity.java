package com.uer.healthcure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(MainActivity.this,GetAllDetails.class);
                startActivity(i1);
            }
        });

        b2=(Button)findViewById(R.id.allusers);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(MainActivity.this,ViewUsers.class);
                startActivity(i1);
            }
        });

        b3=(Button)findViewById(R.id.predictions);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(MainActivity.this,Prediction.class);
                startActivity(i1);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
                  Intent i1= new Intent(MainActivity.this,MainActivity.class);
                  startActivity(i1);
            // Handle the camera action
        } else if (id == R.id.adduser) {
            Intent i1= new Intent(MainActivity.this,AddUser.class);
            startActivity(i1);

        } else if (id == R.id.viewemployee) {

            Intent i1= new Intent(MainActivity.this,ViewEmployee.class);
            startActivity(i1);

        } else if (id == R.id.allotkit) {
            Intent i1= new Intent(MainActivity.this,AllotKit.class);
            startActivity(i1);

        } else if (id == R.id.heartratedignosis) {

            Intent i1= new Intent(MainActivity.this,HeartRateDignosis.class);
            startActivity(i1);

        } else if (id == R.id.oxygenanalysis) {
            Intent i1= new Intent(MainActivity.this,OxyLevelDignosis.class);
            startActivity(i1);
        }else if (id == R.id.temp_analysis) {
            Intent i1= new Intent(MainActivity.this,TemperatureDignosis.class);
            startActivity(i1);
        }else if (id == R.id.atrisk) {
            Intent i1= new Intent(MainActivity.this,AtRisk.class);
            startActivity(i1);
        }
        else if (id == R.id.logout) {
            Intent i1= new Intent(MainActivity.this,Login.class);
            startActivity(i1);
        }
        else if (id == R.id.add_training) {
            Intent i1= new Intent(MainActivity.this,AddTraining.class);
            startActivity(i1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
