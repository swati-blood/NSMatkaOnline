package in.games.nidhimatka.Fragments.pana;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.games.nidhimatka.Adapter.SigleDoublePanaAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;

import static in.games.nidhimatka.Fragments.FragmentDigits.game_names;
import static in.games.nidhimatka.Objects.sp_input_data.doublePanna;
import static in.games.nidhimatka.Objects.sp_input_data.singlePaana;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmentfour extends Fragment {
    RecyclerView rv_digits;
    SigleDoublePanaAdapter sigleDoublePanaAdapter;
    LoadingBar loadingBar ;
    Common common;
    List<String> single_list;

    public Fragmentfour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmentfour, container, false);
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
        }
        else if (game_names.equals("Double Pana"))
        {
            single_list = Arrays.asList(doublePanna);
        }
        sigleDoublePanaAdapter = new SigleDoublePanaAdapter(single_list.subList(30,40),getActivity());
        rv_digits.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv_digits.setAdapter(sigleDoublePanaAdapter);
        sigleDoublePanaAdapter.notifyDataSetChanged();
    }
}
