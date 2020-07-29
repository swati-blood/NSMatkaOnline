package in.games.nidhimatka.Fragments.starline;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Fragments.SelectGameFragment;
import in.games.nidhimatka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarMainFragment extends Fragment {
    String matka_name ,matka_id,start_time ,end_time ,start_num,num , end_num ;
    TextView tv_m_name ,tv_s_time ,tv_end_time,tv_num;
    public static TextView tv_game;
    Common common;

    public StarMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_star_main, container, false);

//        matka_name =getArguments().getString("matka_name");
        matka_id =getArguments().getString("matka_id");
        end_time =getArguments().getString("end_time");
        start_time=getArguments().getString("start_time");
//        start_num=getArguments().getString("start_number");
//        end_num=getArguments().getString("end_number");
//        num=getArguments().getString("number");
        common=new Common(getActivity());
//        tv_end_time= view.findViewById(R.id.bid_close);
//        tv_s_time = view.findViewById(R.id.bid_open);
//        tv_m_name = view.findViewById(R.id.matkaname);
//        tv_game = view.findViewById(R.id.gamename);
//        tv_num = view.findViewById(R.id.matkanumber);
//        ((MainActivity) getActivity()).setTitle(matka_name);
//        tv_s_time.setText(common.get24To12Format(start_time));
//        tv_end_time.setText(common.get24To12Format(end_time));
//
//        tv_game.setText("");
//        tv_m_name.setText(matka_name);
//
//        tv_num.setText(start_num+" - "+num+" - "+end_num);



        Bundle bundle = new Bundle();
//        bundle.putString("matka_name",getArguments().getString("matka_name"));
        bundle.putString("m_id",getArguments().getString("m_id"));
        bundle.putString("end_time",getArguments().getString("end_time"));
        bundle.putString("start_time",getArguments().getString("start_time"));
//        bundle.putString("start_num",start_num.toString());
//        bundle.putString("num",num.toString());
//        bundle.putString("end_num",end_num.toString());
        Fragment fm  = new StarlineGameFragment();
        fm.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_frame, fm)
                .commit();
        return  view;
    }
    }

