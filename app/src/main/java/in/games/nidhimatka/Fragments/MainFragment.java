package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.games.nidhimatka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

String matka_name ,matka_id,start_time ,end_time ,start_num,num , end_num ;
TextView tv_m_name ,tv_s_time ,tv_end_time,tv_num;
 public static TextView tv_game;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_main, container, false);
      matka_name =getArguments().getString("matka_name");
      matka_id =getArguments().getString("matka_id");
      end_time =getArguments().getString("end_time");
     start_time=getArguments().getString("start_time");
     start_num=getArguments().getString("start_number");
     end_num=getArguments().getString("end_number");
     num=getArguments().getString("number");
     tv_end_time= view.findViewById(R.id.bid_close);
     tv_s_time = view.findViewById(R.id.bid_open);
     tv_m_name = view.findViewById(R.id.matkaname);
     tv_game = view.findViewById(R.id.gamename);
     tv_num = view.findViewById(R.id.matkanumber);

        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        try {
            Date _24Hourst = _24HourSDF.parse(start_time);
            Date _24Houret = _24HourSDF.parse(end_time);
           String s_date = _12HourSDF.format(_24Hourst);
           String e_date = _12HourSDF.format(_24Houret);

            tv_s_time.setText(s_date);
            tv_end_time.setText(e_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

     tv_game.setText("");
     tv_m_name.setText(matka_name);

     tv_num.setText(start_num+" - "+num+" - "+end_num);



        Bundle bundle = new Bundle();
        bundle.putString("matka_name",getArguments().getString("matka_name"));
        bundle.putString("m_id",getArguments().getString("m_id"));
        bundle.putString("end_time",getArguments().getString("end_time"));
        bundle.putString("start_time",getArguments().getString("start_time"));
        Fragment fm  = new SelectGameFragment();
        fm.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_frame, fm)
                .commit();
      return  view;
    }
}
