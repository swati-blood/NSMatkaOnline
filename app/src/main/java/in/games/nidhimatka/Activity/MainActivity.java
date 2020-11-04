package in.games.nidhimatka.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import in.games.nidhimatka.Adapter.MatakListViewAdapter;
import in.games.nidhimatka.Adapter.MatkaAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Fragments.AddFunds;
import in.games.nidhimatka.Fragments.AllHistoryFragment;
import in.games.nidhimatka.Fragments.GameRatesFragment;
import in.games.nidhimatka.Fragments.GenerateMpinFragment;
import in.games.nidhimatka.Fragments.HistroyFragment;
import in.games.nidhimatka.Fragments.HomeFragment;
import in.games.nidhimatka.Fragments.HowToPLayFragment;
import in.games.nidhimatka.Fragments.MyProfileFragment;
import in.games.nidhimatka.Fragments.NoticeFragment;
import in.games.nidhimatka.Fragments.ProfileFragment;
import in.games.nidhimatka.Fragments.WalletFragment;
import in.games.nidhimatka.Fragments.WithdrawFundsFragment;
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
TextView txt_wallet,txtUserName ,txt_title ;
    DrawerLayout drawer;
    Toolbar toolbar;
    Session_management session_management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txt_wallet = findViewById(R.id.txtWallet);
//        txt_title = findViewById(R.id.tv_title);
      session_management=new Session_management(activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, toolbar.getPaddingTop(),0, toolbar.getPaddingBottom());
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
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


//        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        Fragment fm = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    Fragment fr = getSupportFragmentManager().findFragmentById(R.id.contentPanel);

                    final String fm_name = fr.getClass().getSimpleName();
                    Log.e("backstack: ", ": " + fm_name);
                    if (fm_name.contentEquals("HomeFragment")) {
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        toggle.setDrawerIndicatorEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        toggle.syncState();

                    }
                    else {

                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        toggle.setDrawerIndicatorEnabled(false);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        toggle.syncState();

                        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                onBackPressed();
                            }
                        });
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
            new Common(activity).getWalletAmount();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem wallet_item = menu.findItem(R.id.action_wallet);
        final MenuItem notification_item = menu.findItem(R.id.action_notification);
        wallet_item.setVisible(true);
        notification_item.setVisible(true);
        View count_wallet = wallet_item.getActionView();
//        View count_notify =notification_item.getActionView();
//       count_wallet.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                menu.performIdentifierAction(wallet_item.getItemId(), 0);
//
//            }
//        });

        txt_wallet = (TextView) wallet_item.getActionView().findViewById(R.id.tv_menu_wallet);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fm = null;

        if (id == R.id.nav_profile) {
           fm = new MyProfileFragment();


        }
        else if (id == R.id.nav_home)
        {
           fm = new HomeFragment();

        }
        else if (id == R.id.nav_add)
        {
            Intent intent=new Intent(MainActivity.this,AddFundRequestActivity.class);
            startActivity(intent);
//            fm = new AddFunds();

        }
        else if (id == R.id.nav_withdrw)
        {
            fm = new WithdrawFundsFragment();

        }
        else if (id == R.id.nav_mpin) {

            fm = new GenerateMpinFragment();

        } else if (id == R.id.nav_how_toPlay) {

            fm = new HowToPLayFragment();

        }
        else if (id == R.id.nav_history) {
            Bundle bundle = new Bundle();
            bundle.putString("type","bid");
            fm = new AllHistoryFragment();
            fm.setArguments(bundle);

        }
        else if (id == R.id.nav_wallet) {

            Bundle bundle = new Bundle();
            bundle.putString("type","funds");
            fm = new AllHistoryFragment();
            fm.setArguments(bundle);
        }

        else if (id == R.id.nav_gameRates) {
            fm = new GameRatesFragment();

        }
        else if (id == R.id.nav_noticeBoard) {
            fm = new NoticeFragment();


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

        if(fm!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null)
                    .commit();

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    public void setWallet_Amount(String wallet) {
        try {
            txt_wallet.setText(wallet);
        } catch (Exception e) {

        }
    }
    public String getWallet()
    {
        String s = txt_wallet.getText().toString().trim();
        return s;
    }
}
