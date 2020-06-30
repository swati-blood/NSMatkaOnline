package in.games.nidhimatka.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import in.games.nidhimatka.Adapter.MatakListViewAdapter;
import in.games.nidhimatka.Adapter.MatkaAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Fragments.GameRatesFragment;
import in.games.nidhimatka.Fragments.GenerateMpinFragment;
import in.games.nidhimatka.Fragments.HistroyFragment;
import in.games.nidhimatka.Fragments.HomeFragment;
import in.games.nidhimatka.Fragments.HowToPLayFragment;
import in.games.nidhimatka.Fragments.MyProfileFragment;
import in.games.nidhimatka.Fragments.NoticeFragment;
import in.games.nidhimatka.Fragments.ProfileFragment;
import in.games.nidhimatka.Fragments.WalletFragment;
import in.games.nidhimatka.Model.MatkaObject;
import in.games.nidhimatka.Model.MatkasObjects;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.Session_management;

import static in.games.nidhimatka.Config.Constants.KEY_NAME;
import static in.games.nidhimatka.Config.Constants.KEY_WALLET;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
Activity activity = MainActivity.this;
TextView txt_wallet,txtUserName ;
    DrawerLayout drawer;
    Session_management session_management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        txt_wallet = findViewById(R.id.txtWallet);
      session_management=new Session_management(activity);

      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(activity.getResources().getColorStateList(R.color.colorPrimaryDark));
        txtUserName=(TextView)navigationView.getHeaderView(0).findViewById(R.id.user_mobile);
        if(session_management.getUserDetails().get(KEY_NAME).toString().isEmpty() || session_management.getUserDetails().get(KEY_NAME).toString().equals(""))
        {

        }
        else {
            txtUserName.setText(session_management.getUserDetails().get(KEY_NAME).toString());
        }

//        toolbar.setPadding(0, toolbar.getPaddingTop(),0, toolbar.getPaddingBottom());
//
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        Fragment fm = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        txt_wallet.setText(session_management.getUserDetails().get(KEY_WALLET).toString());
//        new Common(activity).setWallet_Amount(txt_wallet,new LoadingBar(activity), Prevalent.currentOnlineuser.getId());
    }
    public void setWalletCounter(String walletAmount)
    {
        txt_wallet.setText(walletAmount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fm = null;

        if (id == R.id.nav_profile) {
           fm = new MyProfileFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (id == R.id.nav_home)
        {
           fm = new HomeFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (id == R.id.nav_mpin) {

            fm = new GenerateMpinFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        } else if (id == R.id.nav_how_toPlay) {

            fm = new HowToPLayFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (id == R.id.nav_history) {

            fm = new HistroyFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();

        }
        else if (id == R.id.nav_wallet) {

            fm = new WalletFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();

        }

        else if (id == R.id.nav_gameRates) {
            fm = new GameRatesFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (id == R.id.nav_noticeBoard) {
            fm = new NoticeFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();

        }
        else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("LOGOUT?")
                    .setCancelable(false)
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            session_management.logoutSession();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    public void setTitle(String title) {
//        getSupportActionBar().setTitle(title);
    }
}
