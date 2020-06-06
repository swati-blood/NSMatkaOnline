package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import in.games.nidhimatka.Adapter.RecyclerPagerAdapter;
import in.games.nidhimatka.R;

import static in.games.nidhimatka.Objects.sp_input_data.group_jodi_digits;
import static in.games.nidhimatka.Objects.sp_input_data.panel_group_array;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayBetFragment extends Fragment {
    RecyclerView rv_pager;
    RecyclerPagerAdapter pagerAdapter;
    List<String > digit_list;

    public PlayBetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_play_bet, container, false);
       rv_pager = view.findViewById(R.id.rv_digits);
        digit_list =  Arrays.asList(panel_group_array);
        Log.e("list_size", String.valueOf(digit_list.size()));


       return  view ;
    }
}
