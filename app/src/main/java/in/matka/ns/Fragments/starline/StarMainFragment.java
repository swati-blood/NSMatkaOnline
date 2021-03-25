package in.matka.ns.Fragments.starline;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.Common.Common;
import in.matka.ns.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarMainFragment extends Fragment {
    String matka_name ,matka_id,start_time ,end_time ,start_num,num , end_num ;
    TextView tv_m_name ,tv_s_time ,tv_end_time,tv_num;
    public static TextView tv_star_date,tv_star_time,star_game_name;
    Common common;

    public StarMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_star_main, container, false);

        matka_id =getArguments().getString("matka_id");
        end_time =getArguments().getString("end_time");
        start_time=getArguments().getString("start_time");

        common=new Common(getActivity());
        tv_star_date= view.findViewById(R.id.tv_date);
        tv_star_time = view.findViewById(R.id.tv_time);
//        tv_m_name = view.findViewById(R.id.matkaname);
     star_game_name = view.findViewById(R.id.game_name);
//        tv_num = view.findViewById(R.id.matkanumber);
        ((MainActivity) getActivity()).setTitle("StarLine Games");
      tv_star_time.setText(common.get24To12Format(start_time));
        Date date = new Date();

        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");

        String ctt = dFormat.format(date);
        tv_star_date.setText(ctt);

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

