package in.matka.NS.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import in.matka.NS.Activity.MainActivity;
import in.matka.NS.Common.Common;
import in.matka.NS.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

String matka_name ,matka_id,start_time ,end_time ,start_num,num , end_num ;
TextView tv_m_name ,tv_s_time ,tv_end_time,tv_num;
 public static TextView tv_game;
 Common common;
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
     common=new Common(getActivity());
     tv_end_time= view.findViewById(R.id.bid_close);
     tv_s_time = view.findViewById(R.id.bid_open);
     tv_m_name = view.findViewById(R.id.matkaname);
     tv_game = view.findViewById(R.id.gamename);
     tv_num = view.findViewById(R.id.matkanumber);
        ((MainActivity) getActivity()).setTitle(matka_name);
            tv_s_time.setText(common.get24To12Format(start_time));
            tv_end_time.setText(common.get24To12Format(end_time));

     tv_game.setText("");
     tv_m_name.setText(matka_name);
     if(common.checkNullString(end_num)){
         tv_num.setText(start_num+" - "+num);
     }else{
         tv_num.setText(start_num+" - "+num+" - "+end_num);
     }




        Bundle bundle = new Bundle();
        bundle.putString("matka_name",getArguments().getString("matka_name"));
        bundle.putString("m_id",getArguments().getString("m_id"));
        bundle.putString("end_time",getArguments().getString("end_time"));
        bundle.putString("start_time",getArguments().getString("start_time"));
        bundle.putString("start_num",start_num.toString());
        bundle.putString("num",num.toString());
        bundle.putString("end_num",end_num.toString());
        Fragment fm  = new SelectGameFragment();
        fm.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_frame, fm)
                .commit();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
               try {
                   InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                   inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
                   Fragment fr=getActivity().getSupportFragmentManager().findFragmentById(R.id.container_frame);
                   String frName=fr.getClass().getSimpleName();
                   Log.e("Fragment_main", "onBackStackChanged: "+frName);
                   if(frName.contentEquals("SelectGameFragment")){
                       tv_game.setText("");
                   }
               }catch (Exception ex){
                   ex.printStackTrace();
               }
            }
        });
      return  view;
    }
}
