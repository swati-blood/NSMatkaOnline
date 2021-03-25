package in.matka.ns.Fragments.pana;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.matka.ns.Adapter.SigleDoublePanaAdapter;
import in.matka.ns.Common.Common;
import in.matka.ns.R;
import in.matka.ns.Util.LoadingBar;

import static in.matka.ns.Fragments.FragmentDigits.game_names;
import static in.matka.ns.Objects.sp_input_data.doublePanna;
import static in.matka.ns.Objects.sp_input_data.singlePaana;


public class Fragmenteleven extends Fragment {
    RecyclerView rv_digits;
    SigleDoublePanaAdapter sigleDoublePanaAdapter;
    LoadingBar loadingBar ;
    Common common;
    List<String> single_list;
    public Fragmenteleven() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_eleven, container, false);
       initViews(view);
       return view ;
    }
    void initViews(View v)
    {
        rv_digits = v.findViewById(R.id.rv_digits);
        common = new Common(getActivity());
        loadingBar = new LoadingBar(getActivity());
        single_list = new ArrayList<>();

        if (game_names.equals("Single Pana"))
        {
            single_list = Arrays.asList(singlePaana);
            Log.d("single", String.valueOf(single_list.size()));
        }
        else if (game_names.equals("Double Pana"))
        {
            single_list = Arrays.asList(doublePanna);
        }
//        sigleDoublePanaAdapter = new SigleDoublePanaAdapter(single_list.subList(90,100),getActivity());
//        rv_digits.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        rv_digits.setAdapter(sigleDoublePanaAdapter);
//        sigleDoublePanaAdapter.notifyDataSetChanged();
    }
}
