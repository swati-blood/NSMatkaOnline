package in.matka.rose.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import in.matka.rose.Activity.MainActivity;
import in.matka.rose.Adapter.PagerAdapter;
import in.matka.rose.Common.Common;
import in.matka.rose.Model.TableModel;
import in.matka.rose.R;
import in.matka.rose.Util.LoadingBar;

import static in.matka.rose.Objects.sp_input_data.singlePaana;

public class FragmentDigits extends Fragment implements View.OnClickListener {
    TextView txt_date,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    public static TextView txt_type,total  ;
    Dialog dialog ;
    RecyclerView rv_points;
    TabLayout tablayout;
    public static ArrayList<String> panaPointsList;
   public static String game_names, matka_names, matka_id ,game_ids ,w_amounts ,s_time ,e_time;
    List<String> list;
    public static ArrayList<TableModel> singlepana_list, doublepana_list,tempList_single,tempList_double;
    Button btn_submit;
    Common common;
    LoadingBar loadingBar;
    ViewPager viewpager ;
    int selected_pos =0;
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
        panaPointsList=new ArrayList<>();
      tempList_double = new ArrayList<>();
       tempList_single= new ArrayList<>();
       singlepana_list = new ArrayList<>();
        doublepana_list = new ArrayList<>();
        common = new Common(getActivity());

        loadingBar = new LoadingBar(getActivity());
        matka_names = getArguments().getString("matka_name");
        game_names = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_ids = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amounts = ((MainActivity) getActivity()).getWallet();
        bet_type = txt_type.getText().toString();
        btn_submit.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        total.setOnClickListener(this);
        for(int i=0; i<singlePaana.length;i++)
        {
            panaPointsList.add("0");
        }
        ((MainActivity) getActivity()).setTitle(matka_names+"-"+game_names);
        setTabLayout();


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
    public void onClick(View v) {

        if (v.getId()==R.id.tv_type)
        {
            common.setBetTypeDialog(dialog,txtOpen,txtClose,txt_type,txt_date.getText().toString(),s_time,e_time);
        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,loadingBar);
        }
    }
    void setTabLayout() {
        tablayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < 10; i++) {
            int ind = i + 1;
            tablayout.addTab(tablayout.newTab().setText(String.valueOf(ind)), i);

        }
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pagerAdapter=new PagerAdapter(getActivity().getSupportFragmentManager(),10);
        viewpager.setAdapter(pagerAdapter);

    }

    public ArrayList<String> getPanaPointsList()
    {
        return panaPointsList;
    }

    public void updatePanaPointList(String pnts,int pos)
    {
        panaPointsList.set(pos,pnts);
    }
}
