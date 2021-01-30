package in.matka.rose.Fragments.pana;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import in.matka.rose.Activity.PanaActivity;
import in.matka.rose.Adapter.SigleDoublePanaAdapter;
import in.matka.rose.Common.Common;
import in.matka.rose.Intefaces.UpdateTotalBidAmount;
import in.matka.rose.Model.TableModel;
import in.matka.rose.R;
import in.matka.rose.Util.LoadingBar;

import static in.matka.rose.Activity.PanaActivity.game_names;
import static in.matka.rose.Activity.PanaActivity.total;
import static in.matka.rose.Config.Constants.REV_BEFORE_VALUE;
import static in.matka.rose.Config.Constants.REV_BET_TYPE;
import static in.matka.rose.Config.Constants.REV_FRAG_POSITION;
import static in.matka.rose.Config.Constants.REV_POINTS;
import static in.matka.rose.Config.Constants.REV_POSITION;
import static in.matka.rose.Config.Constants.REV_TYPE;
import static in.matka.rose.Objects.sp_input_data.doublePanna;
import static in.matka.rose.Objects.sp_input_data.singlePaana;


public class FragementSeven extends Fragment {

    RecyclerView rv_digits;
    SigleDoublePanaAdapter sigleDoublePanaAdapter;
    LoadingBar loadingBar ;
    Common common;
    List<String> single_list;
    ArrayList<TableModel> bet_list;
    int totAmt=0;

    public FragementSeven() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =inflater.inflate(R.layout.fragment_fragement_seven, container, false);
       initViews(v);
       return v;
    }
    void initViews(View v)
    {
        rv_digits = v.findViewById(R.id.rv_digits);
        common = new Common(getActivity());
        loadingBar = new LoadingBar(getActivity());
        single_list = new ArrayList<>();
        bet_list = new ArrayList<>();

        if (game_names.equals("Single Pana"))
        {
            single_list = Arrays.asList(singlePaana);
        }
        else if (game_names.equals("Double Pana"))
        {
            single_list = Arrays.asList(doublePanna);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        bet_list.clear();
        bet_list.addAll(((PanaActivity)getActivity()).getBidList());
        if (game_names.equals("Single Pana"))
        {
            sigleDoublePanaAdapter = new SigleDoublePanaAdapter(single_list.subList(72,84),getActivity(),7,bet_list,game_names);

        }
        else if (game_names.equals("Double Pana"))
        {
            sigleDoublePanaAdapter = new SigleDoublePanaAdapter(single_list.subList(60,70),getActivity(),7,bet_list,game_names);

        }
        rv_digits.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv_digits.setAdapter(sigleDoublePanaAdapter);
        sigleDoublePanaAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver mPointReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");
            String fragPosition = intent.getStringExtra("fragment_position");
            String points = intent.getStringExtra("point");
            String beforevalue = intent.getStringExtra("beforevalue");
            String bet_type = intent.getStringExtra("bet_type");
            String position = intent.getStringExtra("position");
            if(fragPosition.equals("7")) {
                HashMap<String, String> map = new HashMap<>();
                map.put(REV_TYPE, type);
                map.put(REV_FRAG_POSITION, fragPosition);
                map.put(REV_POINTS, points);
                map.put(REV_BEFORE_VALUE, beforevalue);
                map.put(REV_BET_TYPE, bet_type);
                map.put(REV_POSITION, position);
                totAmt = ((PanaActivity) getActivity()).getTotalBidAmount();
                common.setPanaPoints(map, totAmt, bet_list, game_names,new UpdateTotalBidAmount() {
                    @Override
                    public void updateTotalBidAmount(int amt) {
                        ((PanaActivity) getActivity()).updateTotalBidAmount(amt);
                        total.setText(String.valueOf(amt));
                    }
                });
            }

        }
    };



    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mPointReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mPointReceiver, new IntentFilter("Points"));

    }
}
