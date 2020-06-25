package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.PagerAdapter;
import in.games.nidhimatka.Adapter.PointsAdapter;
import in.games.nidhimatka.Adapter.RecyclerPagerAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;

import static in.games.nidhimatka.Objects.sp_input_data.singlePaana;

public class FragmentDigits extends Fragment implements View.OnClickListener {
    TextView txt_date,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    public static TextView txt_type,total  ;
    Dialog dialog ;
    RecyclerView rv_points;
    TabLayout tablayout;
   public static String game_names, matka_names, matka_ids ,game_ids ,w_amounts ,s_times ,e_times ;
    List<String> list;
    public static ArrayList<TableModel> singlepana_list, doublepana_list,tempList_single,tempList_double;
    Button btn_submit;
    Common common;
    LoadingBar loadingBar;
    ViewPager viewpager ;
    int selected_pos =0 ,no_of_tabs=0;
    public static String bet_type ="";
    String bet_date="";
    PagerAdapter pagerAdapter;

    public FragmentDigits() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_digits, container, false);
       initViews(view);
       return view ;
    }
    private void initViews(View v) {
        txt_date = v.findViewById(R.id.tv_date);
        txt_type = v.findViewById(R.id.tv_type);
        btn_submit = v.findViewById(R.id.btn_sbmit);
        rv_points = v.findViewById(R.id.rv_digits);
        total = v.findViewById(R.id.bet_total);
        tablayout = v.findViewById(R.id.tablayout);
        viewpager = v.findViewById(R.id.viewpager);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        list = new ArrayList<>();
      tempList_double = new ArrayList<>();
       tempList_single= new ArrayList<>();
       singlepana_list = new ArrayList<>();
        doublepana_list = new ArrayList<>();
        common = new Common(getActivity());

        loadingBar = new LoadingBar(getActivity());
        matka_names = getArguments().getString("matka_name");
        game_names = getArguments().getString("game_name");
        matka_ids = getArguments().getString("m_id");
        game_ids = getArguments().getString("game_id");
        s_times = getArguments().getString("start_time");
        e_times = getArguments().getString("end_time");
        w_amounts = txtWalet.getText().toString();
        bet_type = txt_type.getText().toString();
        btn_submit.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        total.setOnClickListener(this);
        ((MainActivity) getActivity()).setTitle(matka_names+"-"+game_names);
        setTabLayout();

        if (game_names.equals("Single Pana"))
        {
            no_of_tabs=12;
        }
        else
        {
            no_of_tabs = 9;
        }
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

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pagerAdapter=new PagerAdapter(getActivity().getSupportFragmentManager(),no_of_tabs);
        viewpager.setAdapter(pagerAdapter);

        Log.e("gme",game_names + game_ids +matka_names+matka_ids);


    }

    @Override
    public void onClick(View v) {

    }
    void setTabLayout() {
        tablayout.setVisibility(View.VISIBLE);
        int len = singlePaana.length;
        for (int i = 0; i < 10; i++) {
            int ind = i + 1;
            tablayout.addTab(tablayout.newTab().setText(String.valueOf(ind)), i);

        }

    }
}
