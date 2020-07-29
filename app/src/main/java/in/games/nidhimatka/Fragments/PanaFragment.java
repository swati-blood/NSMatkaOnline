package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.PointsAdapter;
import in.games.nidhimatka.Adapter.RecyclerPagerAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;

import static in.games.nidhimatka.Adapter.PointsAdapter.getPonitsList;
import static in.games.nidhimatka.Adapter.PointsAdapter.is_empty;
import static in.games.nidhimatka.Adapter.PointsAdapter.is_error;
import static in.games.nidhimatka.Adapter.PointsAdapter.ponitsList;
import static in.games.nidhimatka.Objects.sp_input_data.doublePanna;
import static in.games.nidhimatka.Objects.sp_input_data.group_jodi_array;
import static in.games.nidhimatka.Objects.sp_input_data.group_jodi_digits;
import static in.games.nidhimatka.Objects.sp_input_data.panel_group_array;
import static in.games.nidhimatka.Objects.sp_input_data.singlePaana;
import static in.games.nidhimatka.Objects.sp_input_data.single_digit;

import static in.games.nidhimatka.Objects.sp_input_data.triplePanna;

/**
 * A simple {@link Fragment} subclass.
 */
public class PanaFragment extends Fragment implements View.OnClickListener {
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
 public static String bet_type ="";
          String bet_date="";


    public PanaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pana, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
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
        loadingBar = new LoadingBar(getActivity());
        matka_name = getArguments().getString("matka_name");
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

        bet_type = txt_type.getText().toString();
        btn_submit.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        total.setOnClickListener(this);
        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);
       setLayout(game_name);

       Log.e("gme",game_name + game_id +matka_name+matka_id);


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
            for(int k=0; k<bet_list.size();k++)
            {
                if(bet_list.get(k).getPoints().toString().equals("0") || bet_list.get(k).getPoints().toString().equals(""))
                { }
                else
                {

                  tempList.add(bet_list.get(k));
                }
            }

            bet_type = txt_type.getText().toString();
            bet_date = txt_date.getText().toString();

            if (bet_date.equals("Select Date")) {
                Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_LONG).show();
            } else if (bet_type.equals("Select Type")) {
                Toast.makeText(getActivity(), "Select game type", Toast.LENGTH_LONG).show();

            } else if (is_empty) {
                Toast.makeText(getActivity(), "Please enter some points", Toast.LENGTH_LONG).show();
            } else {
                if (is_error) {
                    Toast.makeText(getActivity(), "Minimum bid amount is 10", Toast.LENGTH_LONG).show();
                } else {
                    int amt = 0;
                    for (int j = 0; j < list.size(); j++) {
                        amt = amt + Integer.parseInt(bet_list.get(j).getPoints());
                    }

                    if (amt > Integer.parseInt(w_amount)) {
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

                                    common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, g_d, game_id, w_amount, matka_name, loadingBar, btn_submit, s_time, e_time);
                                } else if (curr < 0) {
                                    common.setBidsDialog(Integer.parseInt(w_amount), tempList, matka_id, g_d, game_id, w_amount, matka_name, loadingBar, btn_submit, s_time, e_time);
                                } else {

                                    common.errorMessageDialog("Betting is Closed Now");

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
