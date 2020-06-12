package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

import in.games.nidhimatka.Adapter.SelectGameAdapter;
import in.games.nidhimatka.Model.GameModel;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.RecyclerTouchListener;

import static in.games.nidhimatka.Fragments.MainFragment.tv_game;

public class SelectGameFragment extends Fragment {
    RecyclerView rv_games;
    SelectGameAdapter selectGameAdapter ;
    LoadingBar loadingBar ;
    ArrayList<GameModel> game_list;

    public SelectGameFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_select_game, container, false);
       rv_games =  view.findViewById(R.id.recycler_games);
       rv_games.setLayoutManager(new GridLayoutManager(getActivity(),3));
       game_list = new ArrayList<>();
       game_list.add(new GameModel("1","Odd Even","","1"));
       game_list.add(new GameModel("2","Single Digit","","0"));
       game_list.add(new GameModel("3","Jodi Digit","","0"));
       game_list.add(new GameModel("4","Red Bracket","","1"));
       game_list.add(new GameModel("5","Panel Group","","1"));
       game_list.add(new GameModel("6","Group Jodi","","1"));
       game_list.add(new GameModel("7","Single Pana","","0"));
       game_list.add(new GameModel("8","Double Pana","","0"));
       game_list.add(new GameModel("9","Triple Pana","","0"));
       game_list.add(new GameModel("10","SP Motor","","1"));
       game_list.add(new GameModel("11","DP Motor","","1"));
       game_list.add(new GameModel("12","Half Sangam","","1"));
       game_list.add(new GameModel("13","Full Sangam","","1"));
       game_list.add(new GameModel("14","Cycle Pana","","1"));
       selectGameAdapter = new SelectGameAdapter(getActivity(),game_list);
       rv_games.setAdapter(selectGameAdapter);

       rv_games.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_games, new RecyclerTouchListener.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {

               GameModel model = game_list.get(position);
               tv_game.setText(model.getName());
               Fragment fm = null ;
               final Bundle arg = new Bundle();
               arg.putString("game_id",model.getId());
               arg.putString("game_name",model.getName());
               arg.putString("m_id",getArguments().getString("m_id"));
               arg.putString("matka_name",getArguments().getString("matka_name"));
               arg.putString("start_time",getArguments().getString("start_time"));
               arg.putString("end_time",getArguments().getString("end_time"));
               if( model.getType().equals("0"))
               {


                  fm = new PanaFragment();


               }
               else
               {


                   switch (model.getName())
                    {
                        case  "Half Sangam" :
                          fm = new HalfSangamFragment();
                            break;
                        case  "DP Motor" :
                        case  "SP Motor" :
                            fm = new SPMotor();
                            break;
                        case  "Cycle Pana" :
                        fm = new CyclePana();
                        break;
                        case "Full Sangam" :
                            fm = new FullSangamFragmnet();
                            break;
                            case "Panel Group" :
                            fm = new GroupPanel();
                            break;
                            case "Group Jodi" :
                            fm = new GroupJodi();
                            break;
                        case "Red Bracket": fm = new RedBracketFragment();
                            break;
                        case "Odd Even" : fm = new OddEvenFragment();
                            break;
                        default:  fm = new SelectGameFragment();
                        break;


                    }
               }
               fm.setArguments(arg);
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               fragmentManager.beginTransaction().replace(R.id.container_frame, fm)
                       .addToBackStack(null).commit();

           }

           @Override
           public void onLongItemClick(View view, int position) {

           }
       }));


       return  view ;
    }
}
