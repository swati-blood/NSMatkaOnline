package in.matka.ns.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.matka.ns.Adapter.MenuAdapter;
import in.matka.ns.AppController;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.Fragments.AllHistoryFragment;
import in.matka.ns.Fragments.GameRatesFragment;
import in.matka.ns.Fragments.GenerateMpinFragment;
import in.matka.ns.Fragments.HomeFragment;
import in.matka.ns.Fragments.NoticeFragment;
import in.matka.ns.Fragments.UserFragment;
import in.matka.ns.Fragments.WithdrawFundsFragment;
import in.matka.ns.Model.MenuModel;
import in.matka.ns.R;
import in.matka.ns.Util.CustomJsonRequest;
import in.matka.ns.Util.Session_management;

import static in.matka.ns.Config.Constants.KEY_NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
Activity activity = MainActivity.this;
TextView txt_wallet,txtUserName ,txt_title ;
    DrawerLayout drawer;
    RecyclerView list_menu;
    Toolbar toolbar;
    MenuAdapter menuAdapter;
    ArrayList<MenuModel> menuList;
    Common common;
    TextView user_mobile;
    NavigationView navigationView;


    Session_management session_management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // user_mobile=(TextView)findViewById (R.id.user_mobile);

//        txt_wallet = findViewById(R.id.txtWallet);
//        txt_title = findViewById(R.id.tv_title);
        common=new Common (activity);
      session_management=new Session_management(activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        String name="";
        name=session_management.getUserDetails().get(KEY_NAME);
        Log.e ("checkname", "onCreate: "+name);

      // user_mobile.setText (name);

        //list_menu = findViewById(R.id.list_menu);
        menuList = new ArrayList<>();
        toolbar.setPadding(0, toolbar.getPaddingTop(),0, toolbar.getPaddingBottom());
        setSupportActionBar(toolbar);
       // getMenu();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtUserName=(TextView)navigationView.getHeaderView(0).findViewById(R.id.user_mobile);
        if(session_management.getUserDetails().get(KEY_NAME).toString().isEmpty() || session_management.getUserDetails().get(KEY_NAME).toString().equals(""))
        {

        }
        else {
            txtUserName.setText(session_management.getUserDetails().get(KEY_NAME).toString());
        }
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(activity.getResources().getColorStateList(R.color.colorPrimaryDark));



        common=new Common (activity);
        session_management=new Session_management(activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


//        String name="";
//        name=session_management.getUserDetails().get(KEY_NAME);
//        Log.e ("checkname", "onCreate: "+name);
//        user_mobile.setText (name);

       txtUserName=(TextView)navigationView.getHeaderView(0).findViewById(R.id.user_mobile);


//        list_menu.addOnItemTouchListener(new RecyclerTouchListener(this, list_menu, new RecyclerTouchListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Fragment fm=null;
//                Bundle args = new Bundle();
//                Intent i  = null;
//
//                switch (menuList.get(position).getName()){
//
//                    case "Logout":
//                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//
//                        builder.setMessage("LOGOUT?")
//                                .setCancelable(false)
//                                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        session_management.logoutSession();
//
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog alertDialog=builder.create();
//                        alertDialog.show();
//                        break;
//
//                    case "Home":
//                        //i=new Intent(MainActivity.this, HomeActivity.class);
//                        fm = new HomeFragment();
//                        break;
//
//                    case "Profile":
//                       // i=new Intent(HomeActivity.this, ProfileActivity.class);
//                        fm=new UserFragment ();
//                        break;
//
//                    case "Game History":
//                        Bundle bundle = new Bundle();
//                        bundle.putString("type","bid");
//                        fm = new AllHistoryFragment();
//                        fm.setArguments(bundle);
//                        break;
//
//                    case "Starline History":
////                        i =new Intent(HomeActivity.this, Starline_Activity.class);
//                        break;
//
//                    case "Point History":
//                        Bundle bundle2 = new Bundle();
//                         bundle2.putString("type","bid");
//                         fm = new AllHistoryFragment();
//                         fm.setArguments(bundle2);
//            break;
//                    case "Add Points":
//                        i=new Intent(MainActivity.this,AddFundRequestActivity.class);
//
//                        break;
//                    case "Withdraw Request":
//
//                        fm = new WithdrawFundsFragment();
//
//                        break;
//
//                    case "Game Rates":
//                        fm = new GameRatesFragment();
//                        break;
//                    case "Notice Board/Rules":
//                        fm = new NoticeFragment();
//                        break;
//
//                }
//                if (fm!=null) {
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                            .addToBackStack(null)
//                            .commit();
//
//                }
//                if (i!=null)
//                {
////                    ActivityOptions options =
////                            ActivityOptions.makeCustomAnimation(, R.anim.in_from_right, R.anim.out_to_left);
//                //   startActivity(i , options.toBundle());
//                   startActivity(i);
//
////                            if(fm!=null){
////            FragmentManager fragmentManager = getSupportFragmentManager();
////            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
////                    .addToBackStack(null)
////                    .commit();
//
//        }
//        drawer.closeDrawer(GravityCompat.START);
//                }
//
//
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));



        if(session_management.getUserDetails().get(KEY_NAME).toString().isEmpty() || session_management.getUserDetails().get(KEY_NAME).toString().equals(""))
        {

        }
        else {
          // txtUserName.setText(session_management.getUserDetails().get(KEY_NAME).toString());
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
        //final MenuItem notification_item = menu.findItem(R.id.action_notification);
        wallet_item.setVisible(true);
        //notification_item.setVisible(true);
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
           //fm = new MyProfileFragment();

            fm=new UserFragment ();
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

            fm = new GenerateMpinFragment ();

   }
        //else if (id == R.id.nav_how_toPlay) {
//
//            fm = new HowToPLayFragment();
//
//        }
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
    public void getMenu()
    {
        HashMap<String, String> params = new HashMap<>();
       CustomJsonRequest customJsonRequest= new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_MENU, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");
                    Log.e ("icondata", "onResponse: "+response);
                    for (int i=0;i<array.length();i++){
                        MenuModel menuModel = new MenuModel();
                        JSONObject obj = array.getJSONObject(i);
                        menuModel.setName(obj.getString("name"));
                        menuModel.setId(obj.getString("id"));
                        menuModel.setStatus(obj.getString("status"));
                        menuList.add(menuModel);
                    }
                     Log.e("ksmdjcnhfb", String.valueOf(menuList.size()));
//                    Gson gson= new Gson();
//                    MenuModel menuModel = new MenuModel();
//                    Type listType=new TypeToken<List<MenuModel>>(){}.getType();
//                    menuList=gson.fromJson(response.toString(),listType);
                    for (int i=0; i<menuList.size();i++){
                        Log.e("ldekfjgn", String.valueOf(menuList.size()));
                        switch (menuList.get(i).getName())
                        {
                            case "Home":
                                menuList.get(i).setImg(R.drawable.icons8_home_128px);
                                break;
                            case "Profile":
                                menuList.get(i).setImg(R.drawable.icons8_male_user_240px);
                                break;
                            case "Game History":
                                menuList.get(i).setImg(R.drawable.icons8_payment_history_128px);
                                break;
//                            case "Starline History":
//                                menuList.get(i).setImg(R.drawable.icons8_payment_history_128px);
//                                break;
                            case "Point History":
                                menuList.get(i).setImg(R.drawable.icons8_order_history_100px);
                                break;
//                            case "Add Points":
//                                menuList.get(i).setImg(R.drawable.ic_account_balance_wallet_black_24dp);
//                                break;
//                            case "Withdraw Request":
//                                menuList.get(i).setImg(R.drawable.icons8_receive_cash_240px);
//                                break;
                            case "Game Rates":
                                menuList.get(i).setImg(R.drawable.icons8_coins_100px);
                                break;
                            case "Logout":
                                menuList.get(i).setImg(R.drawable.icons8_logout_rounded_up_128px);
                                break;
                            case "Notice Board/Rules":
                                menuList.get(i).setImg(R.drawable.icons8_bulleted_list_128px);
                                break;
                        }
                    }
                    list_menu.setHasFixedSize(true);
                    list_menu.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    menuAdapter = new MenuAdapter(MainActivity.this,menuList);
                    list_menu.setAdapter(menuAdapter);
                    menuAdapter.notifyDataSetChanged();
                    //  list_menu.setAdapter(menuAdapter);
                    Log.e("timSlot",menuList.size()+" "+response.toString());

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
                Toast.makeText(MainActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest);
    }

    @Override
    public void onClick(View v) {

    }


}
