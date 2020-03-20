package my.TicketLawyers.stablelawfirm.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.TicketLawyers.stablelawfirm.Fragments.AboutusFrag;
import my.TicketLawyers.stablelawfirm.Fragments.ContactusFrag;
import my.TicketLawyers.stablelawfirm.Fragments.HomeFrag;
import my.TicketLawyers.stablelawfirm.Fragments.LocationsFrag;
import my.TicketLawyers.stablelawfirm.Fragments.MyCases;
import my.TicketLawyers.stablelawfirm.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    int x=0;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backk));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        displaySelectedScreen(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displaySelectedScreen(item.getItemId());
        return true;

    }
    private void displaySelectedScreen(int itemId) {

         Fragment fragment = null;
         switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFrag();
                break;
           // case R.id.nav_gallery:
                //fragment = new HireUsFragment();
             //   break;
            case R.id.nav_tools:
                fragment =new LocationsFrag();
                break;
            case R.id.nav_send:
                fragment =new ContactusFrag();
                break;
            case R.id.nav_slideshow:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.customdialog);

                final EditText editText=(EditText)dialog.findViewById(R.id.edittext);


                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // x=0;
                        String aa=editText.getText().toString();

                    if(aa.isEmpty())
                        {
                        Toast.makeText(MainActivity.this, "Enter mail id", Toast.LENGTH_SHORT).show();
                        }
                    else{
                        done(aa);
                    dialog.dismiss();
                    } }
                });

                dialog.show();
                break;
            case R.id.nav_share:
                fragment =new AboutusFrag();
                break; }

     if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void done(String aa) {
        Log.e("99", "done: " );

        Fragment argumentFragment = new MyCases();
        Bundle data = new Bundle();
        data.putString("edttext", aa);
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.content_frame, argumentFragment).commit();

//        Bundle bundle = new Bundle();
//        bundle.putString("edttext", aa);
//// set Fragmentclass Arguments
//        MyCases fragobj = new MyCases();
//        fragobj.setArguments(bundle);
    }


}
