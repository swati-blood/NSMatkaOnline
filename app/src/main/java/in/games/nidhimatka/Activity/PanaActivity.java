package in.games.nidhimatka.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.games.nidhimatka.Adapter.PagerAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.Session_management;

import static in.games.nidhimatka.Adapter.PointsAdapter.is_empty;
import static in.games.nidhimatka.Adapter.PointsAdapter.is_error;
import static in.games.nidhimatka.Config.Constants.KEY_WALLET;
import static in.games.nidhimatka.Objects.sp_input_data.doublePanna;
import static in.games.nidhimatka.Objects.sp_input_data.singlePaana;

public class PanaActivity extends AppCompatActivity implements View.OnClickListener {
   ImageView iv_back;
   TextView tv_title,tv_wallet;
    CardView card_matka ,card_star;
   TextView tv_matkaname,tv_matkanumber,tv_gamename,tv_bid_open,tv_bid_close;
    public static ArrayList<TableModel> bet_list,tempList,bidList;
   int tot=0;
    TextView txt_date,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    public static TextView txt_type,total  ;
    Dialog dialog ;
    Session_management session_management;
   TabLayout tablayout;
    Button btn_submit;
    Common common;
    int m_id=0;
    LoadingBar loadingBar;
    Activity ctx=PanaActivity.this;
   ViewPager viewpager;
   PagerAdapter pagerAdapter;
    public static String game_names, matka_names, matka_id ,game_ids ,w_amounts ,s_time ,e_time,start_num,end_num,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pana);
        initViews();
    }
    private void initViews() {
        tablayout=findViewById(R.id.tablayout);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_wallet=findViewById(R.id.tv_wallet);
        viewpager=findViewById(R.id.viewpager);
        txt_date = findViewById(R.id.tv_date);
        txt_type = findViewById(R.id.tv_type);
        btn_submit = findViewById(R.id.btn_sbmit);
        tv_matkaname=findViewById(R.id.matkaname);
        tv_matkanumber=findViewById(R.id.matkanumber);
        tv_gamename=findViewById(R.id.gamename);
        tv_bid_open=findViewById(R.id.bid_open);
        tv_bid_close=findViewById(R.id.bid_close);
        card_matka = findViewById(R.id.card_matka);
        card_star= findViewById(R.id.card_star);
        total=findViewById(R.id.bet_total);
        iv_back.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        common=new Common(ctx);
        loadingBar=new LoadingBar(ctx);
        session_management=new Session_management(ctx);
        bet_list=new ArrayList<>();
        tempList=new ArrayList<>();
        bidList=new ArrayList<>();

        matka_id = getIntent().getStringExtra("m_id");
        m_id = Integer.parseInt(matka_id);
        s_time = getIntent().getStringExtra("start_time");
        e_time = getIntent().getStringExtra("end_time");

        if (m_id>20)
        {
            card_star.setVisibility(View.VISIBLE);
        }
        else
        {
            card_matka.setVisibility(View.VISIBLE);
            matka_names = getIntent().getStringExtra("matka_name");
            game_names = getIntent().getStringExtra("game_name");
            game_ids = getIntent().getStringExtra("game_id");

            start_num = getIntent().getStringExtra("start_num");
            num = getIntent().getStringExtra("num");
            end_num = getIntent().getStringExtra("end_num");
        }

        setTabLayout();
        if(game_names.equalsIgnoreCase("Single Pana"))
        {
            for(int i=0;i<singlePaana.length;i++)
            {
                bidList.add(new TableModel(singlePaana[i].toString(),"0","Select Type"));
            }
        }
        else if(game_names.equalsIgnoreCase("Double Pana"))
        {
            for(int i=0;i<doublePanna.length;i++)
            {
                bidList.add(new TableModel(doublePanna[i].toString(),"0","Select Type"));
            }
        }

        tv_matkaname.setText(matka_names);
        tv_gamename.setText(game_names);
        tv_matkanumber.setText(start_num+" - "+num+" - "+end_num);
        tv_bid_open.setText(common.get24To12Format(s_time));
        tv_bid_close.setText(common.get24To12Format(e_time));
         viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_wallet.setText(session_management.getUserDetails().get(KEY_WALLET));
    }

    void setTabLayout() {
        tablayout.setVisibility(View.VISIBLE);
        if(game_names.equalsIgnoreCase("Single Pana"))
        {
            for (int i = 0; i < 10; i++) {
                int ind = i + 1;
                tablayout.addTab(tablayout.newTab().setText(String.valueOf(ind)), i);

            }

        }
        else if(game_names.equalsIgnoreCase("Double Pana"))
        {
            for (int i = 0; i < 9; i++) {
                int ind = i + 1;
                tablayout.addTab(tablayout.newTab().setText(String.valueOf(ind)), i);

            }

        }
              tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        if(game_names.equalsIgnoreCase("Single Pana"))
        {
            pagerAdapter=new PagerAdapter(getSupportFragmentManager(),10);

        }
        else if(game_names.equalsIgnoreCase("Double Pana"))
        {
            pagerAdapter=new PagerAdapter(getSupportFragmentManager(),9);

        }
        viewpager.setAdapter(pagerAdapter);

    }
    public void setWalletCounter(String walletAmount)
    {
        tv_wallet.setText(walletAmount);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back)
        {
              finish();
        }
        else if (v.getId()==R.id.tv_type)
        {
            common.setBetTypeDialog(dialog,txtOpen,txtClose,txt_type,txt_date.getText().toString(),s_time,e_time);
        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,loadingBar);
        }
        else if(v.getId()==R.id.btn_sbmit)
        {
            tempList.clear();
            for(int k=0; k<bidList.size();k++)
            {
                if(bidList.get(k).getPoints().toString().equals("0") || bidList.get(k).getPoints().toString().equals("") || bidList.get(k).getPoints().toString().length()<=1)
                { }
                else
                {

                    tempList.add(bidList.get(k));
                }
            }

           String bet_type = txt_type.getText().toString();
           String bet_date = txt_date.getText().toString();

            if (bet_date.equals("Select Date")) {
                Toast.makeText(ctx, "Select Date", Toast.LENGTH_LONG).show();
            } else if (bet_type.equals("Select Type")) {
                Toast.makeText(ctx, "Select game type", Toast.LENGTH_LONG).show();

            } else if (tot==0) {
                Toast.makeText(ctx, "Please enter some points", Toast.LENGTH_LONG).show();
            } else {
                if (String.valueOf(tot).length()<2) {
                    Toast.makeText(ctx, "Minimum bid amount is 10", Toast.LENGTH_LONG).show();
                } else {

                    String w_amount=common.getUserWallet().toString();

                    if (tot > Integer.parseInt(w_amount)) {
                        common.errorMessageDialog("Insufficient Amount");

                    } else {
                        try {
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            String cur_time = format.format(date);
                            String cur_date = sdf.format(date);
                            String g_d = bet_date.substring(0, 10);
                            if (cur_date.equals(g_d)) {
                                Log.e("true", "today");
                                Date s_date = format.parse(s_time);
                                Date e_date = format.parse(e_time);
                                Date c_date = format.parse(cur_time);
                                long difference = c_date.getTime() - s_date.getTime();
                                long as = (difference / 1000) / 60;

                                long diff_close = c_date.getTime() - e_date.getTime();
                                long curr = (diff_close / 1000) / 60;
                                long current_time = c_date.getTime();

                                if (as < 0) {

                                    common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, g_d, game_ids, w_amount, matka_names, loadingBar, btn_submit, s_time, e_time);
                                } else if (curr < 0) {
                                    common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, g_d, game_ids, w_amount, matka_names, loadingBar, btn_submit, s_time, e_time);
                                } else {

                                    common.errorMessageDialog("Betting is Closed Now");

                                }
                            } else {

                                common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, bet_date.substring(0, 10), game_ids, w_amount, matka_names, loadingBar, btn_submit, s_time, e_time);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }


    }
    public ArrayList<TableModel> getBidList()
    {
        return bidList;
    }
    public void updateBidList(ArrayList<TableModel> list)
    {
        bidList.clear();
        bidList.addAll(list);
    }

    public int getTotalBidAmount()
    {
        return tot;
    }

    public void updateTotalBidAmount(int amt)
    {
        tot=amt;
    }
}
