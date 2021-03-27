package in.matka.ns.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.matka.ns.Adapter.PagerAdapter;
import in.matka.ns.Common.Common;
import in.matka.ns.Model.TableModel;
import in.matka.ns.R;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Session_management;
import in.matka.ns.Util.ToastMsg;

import static in.matka.ns.Config.Constants.KEY_WALLET;
import static in.matka.ns.Objects.sp_input_data.doublePanna;
import static in.matka.ns.Objects.sp_input_data.singlePaana;

public class PanaActivity extends AppCompatActivity implements View.OnClickListener {
   ImageView iv_back;
   TextView tv_title,tv_wallet;
   RelativeLayout rel_holder;
    CardView card_matka ,card_star;
   TextView tv_matkaname,tv_matkanumber,tv_gamename,tv_bid_open,tv_bid_close;
    public  ArrayList<TableModel> bet_list,tempList,bidList;

   int tot=0;
    TextView txt_date,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_s_date,txt_s_time;
    public static TextView txt_type,total  ;
    Dialog dialog ;
    Session_management session_management;
   TabLayout tablayout;
    Button btn_submit;
    Common common;
    int m_id=0;
    private int stat=0;
    LoadingBar loadingBar;
    EditText et_points;
    Activity ctx=PanaActivity.this;
   ViewPager viewpager,viewpagerhide;
   PagerAdapter pagerAdapter;
   ToastMsg toastMsg;
    public static String game_names, matka_names, matka_id ,game_ids ,w_amounts ,s_time ,e_time,start_num,end_num,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pana);
        initViews();
    }
    private void initViews() {

        viewpagerhide=findViewById (R.id.viewpagerhide);

        et_points=(EditText)findViewById (R.id.et_points);
       // rel_holder=findViewById (R.id.rel_holder);
        tablayout=findViewById(R.id.tablayout);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_wallet=findViewById(R.id.tv_wallet);
        viewpager=findViewById(R.id.viewpager);
        txt_date = findViewById(R.id.tv_date);
        txt_s_date = findViewById(R.id.txt_date);
        txt_type = findViewById(R.id.tv_type);
        txt_s_time = findViewById(R.id.txt_time);
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
        txt_date.setText(common.getCurrentDateDay());
        txt_date.setClickable(false);

        loadingBar=new LoadingBar(ctx);
        session_management=new Session_management(ctx);
        bet_list=new ArrayList<>();
        tempList=new ArrayList<>();
        bidList=new ArrayList<>();
        toastMsg = new ToastMsg(PanaActivity.this);
        matka_id = getIntent().getStringExtra("m_id");
        m_id = Integer.parseInt(matka_id);
        s_time = getIntent().getStringExtra("start_time");
        e_time = getIntent().getStringExtra("end_time");
        game_names = getIntent().getStringExtra("game_name");
        game_ids = getIntent().getStringExtra("game_id");
        //matka_names=getIntent ().getStringExtra ("matka_name");
        tv_title.setText (matka_names+"-"+game_names);

        if (m_id>20)

        {   tv_title.setText ("Starline  -"+game_names);
            card_matka.setVisibility(View.GONE);
            card_star.setVisibility(View.VISIBLE);
            txt_date.setVisibility(View.GONE);
            txt_type.setVisibility(View.GONE);
            try {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy EEEE");
                SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");

                String ctt = dFormat.format(date);
                String times =common.get24To12Format(s_time);
                Log.e("time",times.toString());
                String ctt_day = dateFormat.format(date);
                txt_s_date.setText(ctt);
                txt_date.setText(ctt_day);
                txt_s_time.setText(times);
                if (common.getTimeDifference(s_time) > 0) {

                    txt_type.setText("Open");

                } else {
                    txt_type.setText("Close");

                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
             }
        else
        {
            card_matka.setVisibility(View.VISIBLE);
            card_star.setVisibility(View.GONE);
            matka_names = getIntent().getStringExtra("matka_name");
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
        viewpagerhide.setAdapter (pagerAdapter);
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
            viewpager.setVisibility (View.VISIBLE);
viewpagerhide.setAdapter (null);
            viewpagerhide.setVisibility (View.GONE);

            //et_points.setEnabled (true);
            common.setBetTypeDialog(dialog,txtOpen,txtClose,txt_type,txt_date.getText().toString(),s_time,e_time);
        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,loadingBar);
        }
        else if(v.getId()==R.id.btn_sbmit)
        {
            tempList.clear();
bet_list.clear ();


            for(int k=0; k<bidList.size();k++)
            {
                if(bidList.get(k).getPoints().toString().equals("0") || bidList.get(k).getPoints().toString().equals("") )
                { }
                else
                {

                    tempList.add(bidList.get(k));
                }
            }

           String bet_type = txt_type.getText().toString();
           String bet_date = txt_date.getText().toString();
//          if(!bet_type.equals("Select Type")){
//              //et_points.setEnabled (true);
//              rel_holder.setVisibility (View.GONE);
//            viewpager.setVisibility (View.VISIBLE);
//
//        }

            if (bet_date.equals("Select Date")) {
//                Toast.makeText(ctx, "Select Date", Toast.LENGTH_LONG).show();
                toastMsg.toastIconError("Select Date");
            } else if (bet_type.equals("Select Type")) {
//                Toast.makeText(ctx, "Select game type", Toast.LENGTH_LONG).show();
                toastMsg.toastIconError("Select game type");

            }

            else if(tempList.isEmpty ()){

                toastMsg.toastIconError ("Add points");

            }
//            else if (tot==0) {
////                Toast.makeText(ctx, "Please enter some points", Toast.LENGTH_LONG).show();
//                toastMsg.toastIconError("Please enter some points");
//            }
           else {
                if (String.valueOf(tot).length()<2) {
//                    Toast.makeText(ctx, "Minimum bid amount is 10", Toast.LENGTH_LONG).show();
                    toastMsg.toastIconError("Minimum bid amount is 10");
                } else {

                    String w_amount=common.getUserWallet().toString();

                    if (tot > Integer.parseInt(w_amount)) {
                        toastMsg.toastIconError("Insufficient Amount");

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

//                                    common.errorMessageDialog("Betting is Closed Now");
                                   toastMsg.toastIconError("Biding is Closed Now");

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
