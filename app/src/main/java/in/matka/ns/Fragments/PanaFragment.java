package in.matka.ns.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.Adapter.PointsAdapter;
import in.matka.ns.Adapter.RecyclerPagerAdapter;
import in.matka.ns.Common.Common;
import in.matka.ns.Model.TableModel;
import in.matka.ns.R;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.ToastMsg;

import static in.matka.ns.Adapter.PointsAdapter.getPonitsList;
import static in.matka.ns.Adapter.PointsAdapter.is_empty;
import static in.matka.ns.Adapter.PointsAdapter.is_error;
import static in.matka.ns.Fragments.starline.StarMainFragment.star_game_name;
import static in.matka.ns.Objects.sp_input_data.doublePanna;
import static in.matka.ns.Objects.sp_input_data.group_jodi_digits;
import static in.matka.ns.Objects.sp_input_data.singlePaana;
import static in.matka.ns.Objects.sp_input_data.single_digit;

import static in.matka.ns.Objects.sp_input_data.triplePanna;

/**
 * A simple {@link Fragment} subclass.
 */
public class PanaFragment extends Fragment implements View.OnClickListener {
    EditText et_points;
    TextView txt_date,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
   public static TextView txt_type,total  ;
    Dialog dialog ;
    RecyclerView rv_points;
    TabLayout tabLayout;
    String game_name, matka_name, matka_id ,game_id ,w_amount ,s_time ,e_time ;
    PointsAdapter pointsAdapter;
    RecyclerPagerAdapter recyclerPagerAdapter;
    List<String> list;
    List<String> sp_list;
    List<String> dp_list;
    List<String> single_pana_list;
   public static ArrayList<TableModel> bet_list,tempList;
    Button btn_submit;
    Common common;
    LoadingBar loadingBar;
    ViewPager viewPager ;
    int selected_pos =0;
    int m_id =0;
 public static String bet_type ="";
          String bet_date="";
    ToastMsg toastMsg ;

    public PanaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pana, container, false);
        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        et_points=(EditText)v.findViewById (R.id.et_points);
        txt_date = v.findViewById(R.id.tv_date);
        txt_type = v.findViewById(R.id.tv_type);
        btn_submit = v.findViewById(R.id.btn_sbmit);
        rv_points = v.findViewById(R.id.rv_digits);
        total = v.findViewById(R.id.bet_total);
        tabLayout = v.findViewById(R.id.tablayout);
        viewPager = v.findViewById(R.id.viewpager);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        list = new ArrayList<>();
        bet_list = new ArrayList<>();
        tempList = new ArrayList<>();
        single_pana_list = new ArrayList<>();
        common = new Common(getActivity());
         toastMsg = new ToastMsg(getActivity());
        loadingBar = new LoadingBar(getActivity());
        game_name = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_id = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amount =((MainActivity) getActivity()).getWallet();
        if (matka_id.equalsIgnoreCase("3"))
        {
            txt_type.setVisibility(View.GONE);
            bet_type="Close";
        }
        else
        {
            txt_type.setVisibility(View.VISIBLE);
        }

        bet_type = txt_type.getText().toString();
        btn_submit.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        total.setOnClickListener(this);

        txt_date.setText(common.getCurrentDateDay());
        txt_date.setClickable(false);
       setLayout(game_name);
        m_id= Integer.parseInt(matka_id);
       Log.e("gme",game_name + game_id +matka_name+matka_id);
        if (m_id>20)
        {   ((MainActivity) getActivity()).setTitle("Starline"+"-"+game_name);


            //star_game_name.setText(game_name);
            star_game_name.setText("Starline Game");
            txt_date.setVisibility(View.GONE);
            txt_type.setVisibility(View.GONE);
            try {

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
            txt_date.setVisibility(View.VISIBLE);
            txt_type.setVisibility(View.VISIBLE);
            matka_name = getArguments().getString("matka_name");



        }

    }

    void setTabLayout() {
        tabLayout.setVisibility(View.VISIBLE);
        int len = singlePaana.length;
         for (int i = 0; i < 10; i++) {
            int ind = i + 1;
            tabLayout.addTab(tabLayout.newTab().setText(String.valueOf(ind)), i);

        }

            if (game_name.equalsIgnoreCase("single pana")) {
                list = Arrays.asList(singlePaana);
            } else if (game_name.equalsIgnoreCase("double pana")) {
//                list = Arrays.asList(single_p1);
            }

        rv_points.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


              tabLayout.selectTab(tabLayout.getTabAt(selected_pos));


            }
        });
//        tabLayout.getTabAt(selected_pos).select();
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

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
    public void onClick(View v) {

        if(v.getId() == R.id.bet_total)
        {
            for(int i=0; i<getPonitsList().size();i++)
            {
                Log.e("temp_list_data",""+getPonitsList().get(i).toString());
            }
        }
        else if (v.getId() == R.id.btn_sbmit) {
            tempList.clear();
          //  et_points.setText ("");
            for(int k=0; k<bet_list.size();k++)
            {
                if(bet_list.get(k).getPoints().toString().equals("0") || bet_list.get(k).getPoints().toString().equals(""))
                {

                }


                else
                {

                  tempList.add(bet_list.get(k));
                }
            }

            bet_type = txt_type.getText().toString();
            bet_date = txt_date.getText().toString();
// if(!et_points.getText ().equals ("")){
//     tempList.clear ();
//                et_points.setText ("");
//
//            }
 if (bet_date.equals("Select Date")) {
                toastMsg.toastIconError("Select Date");
            } else if (bet_type.equals("Select Type")) {
                toastMsg.toastIconError("Select game type");

            }
   else if(tempList.isEmpty ()){
    toastMsg.toastIconError ("Add points");

            }


//            else if(et_points.equals ("")){
//                toastMsg.toastIconError ("Please enter some points here");
//           }
            else if (is_empty) {
                toastMsg.toastIconError("Please enter some points");
            }
            else {

                if (is_error) {
                    toastMsg.toastIconError( "Minimum bid amount is 10");
                } else {
                    int amt = 0;
                    for (int j = 0; j < list.size(); j++) {
                        amt = amt + Integer.parseInt(bet_list.get(j).getPoints());
                    }

                    if (amt > Integer.parseInt(w_amount)) {
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

                                    common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, g_d, game_id, w_amount, matka_name, loadingBar, btn_submit, s_time, e_time);
                                } else if (curr < 0) {
                                    common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, g_d, game_id, w_amount, matka_name, loadingBar, btn_submit, s_time, e_time);
                                } else {

                                    toastMsg.toastIconError("Biding is Closed Now");

                                }
                            } else {

                                common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, bet_date.substring(0, 10), game_id, w_amount, matka_name, loadingBar, btn_submit, s_time, e_time);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else if (v.getId()==R.id.tv_type)
        {
            common.setBetTypeDialog(dialog,txtOpen,txtClose,txt_type,txt_date.getText().toString(),s_time,e_time);
            setLayout(game_name);


        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,loadingBar);
        }

    }


    public void setLayout(String gamename)
    {
        switch (gamename)
        {
            case  "Single Digit" :
                addAdapter(rv_points,pointsAdapter,Arrays.asList(single_digit));
                break;
            case  "Jodi Digit" :
                addAdapter(rv_points,pointsAdapter,Arrays.asList(group_jodi_digits));
                break;

            case  "Single Pana" :
                setTabLayout();
                addRecyclerAdapter(rv_points,recyclerPagerAdapter,Arrays.asList(singlePaana));
                break;
            case  "Double Pana" :
                setTabLayout();
               addRecyclerAdapter(rv_points,recyclerPagerAdapter,Arrays.asList(doublePanna));
                break;
            case  "Triple Pana" :
                addAdapter(rv_points,pointsAdapter,Arrays.asList(triplePanna));
                break;


        }
    }

    public void addAdapter(RecyclerView rv_points ,PointsAdapter pAdapter, List<String> list)
    {
        pAdapter = new PointsAdapter(list, getActivity());
        rv_points.setAdapter(pAdapter);
        rv_points.setLayoutManager(new GridLayoutManager(getActivity(), 2));
       // Log.e("list", String.valueOf(list.size()));
    }
  public void addRecyclerAdapter(RecyclerView rv_points ,RecyclerPagerAdapter recyclerPagerAdapter, List<String> list)
    {
        recyclerPagerAdapter = new RecyclerPagerAdapter(list,getActivity(),bet_type,total);
        final LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        rv_points.setLayoutManager(linearLayoutManager);
        rv_points.setAdapter(recyclerPagerAdapter);
        recyclerPagerAdapter.notifyDataSetChanged();
    }


}
