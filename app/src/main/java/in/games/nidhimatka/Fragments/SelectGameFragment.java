package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;

import java.util.ArrayList;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Activity.PanaActivity;
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
    Animation animation;
    int[] animationList = {R.anim.zoom_in, R.anim.slide_down, R.anim.slide_up, R.anim.bounce};
   LayoutAnimationController controller;
    int i = 0;
    public SelectGameFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_select_game, container, false);
        ((MainActivity) getActivity()).setTitle("Select Game");
       rv_games =  view.findViewById(R.id.recycler_games);
       rv_games.setLayoutManager(new GridLayoutManager(getActivity(),3));
       game_list = new ArrayList<>();
       game_list.add(new GameModel("1","Odd Even",R.drawable.odd_even,"1"));
       game_list.add(new GameModel("2","Single Digit",R.drawable.single_digit,"0"));
       game_list.add(new GameModel("3","Jodi Digit",R.drawable.jodi_digits,"0"));
       game_list.add(new GameModel("4","Red Bracket",R.drawable.red_brackets,"1"));
       game_list.add(new GameModel("5","Panel Group",R.drawable.panel_group_icon,"1"));
       game_list.add(new GameModel("6","Group Jodi",R.drawable.group_jodi,"1"));
       game_list.add(new GameModel("7","Single Pana",R.drawable.single_pana,"2"));
       game_list.add(new GameModel("8","Double Pana",R.drawable.double_pana,"2"));
       game_list.add(new GameModel("9","Triple Pana",R.drawable.triple_pana,"0"));
       game_list.add(new GameModel("10","SP Motor",R.drawable.sp_motor,"1"));
       game_list.add(new GameModel("11","DP Motor",R.drawable.dp_motor,"1"));
       game_list.add(new GameModel("12","Half Sangam",R.drawable.half_sangam_digit,"1"));
       game_list.add(new GameModel("13","Full Sangam",R.drawable.full_sangam_digits,"1"));
       game_list.add(new GameModel("14","Cycle Pana",R.drawable.cyclepana,"1"));
       selectGameAdapter = new SelectGameAdapter(getActivity(),game_list, getArguments().getString("m_id"),
        getArguments().getString("matka_name"),
       getArguments().getString("start_time"),
       getArguments().getString("end_time"), getArguments().getString("start_num"),
       getArguments().getString("num"),
     getArguments().getString("end_num"));
       rv_games.setAdapter(selectGameAdapter);

//        animation = AnimationUtils.loadAnimation(getActivity(),
//                R.anim.zoom_in);

//        rv_games.notifyDataSetChanged();
//       rv_games.scheduleLayoutAnimation();
//       rv_games.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_games, new RecyclerTouchListener.OnItemClickListener() {
//           @Override
//           public void onItemClick(View view, int position) {
//
//
//               GameModel model = game_list.get(position);
//               tv_game.setText(model.getName());
//               Fragment fm = null ;
//               if( model.getType().equals("0"))
//               {
//                   fm = new PanaFragment();
//
//                   final Bundle arg = new Bundle();
//                   arg.putString("game_id",model.getId());
//                   arg.putString("game_name",model.getName());
//                   arg.putString("m_id",getArguments().getString("m_id"));
//                   arg.putString("matka_name",getArguments().getString("matka_name"));
//                   arg.putString("start_time",getArguments().getString("start_time"));
//                   arg.putString("end_time",getArguments().getString("end_time"));
//                   fm.setArguments(arg);
//                   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                   fragmentManager.beginTransaction().replace(R.id.container_frame, fm)
//                           .addToBackStack(null).commit();
//
//               }
//               else if( model.getType().equals("1"))
//               {
//
//                   final Bundle arg = new Bundle();
//                   arg.putString("game_id",model.getId());
//                   arg.putString("game_name",model.getName());
//                   arg.putString("m_id",getArguments().getString("m_id"));
//                   arg.putString("matka_name",getArguments().getString("matka_name"));
//                   arg.putString("start_time",getArguments().getString("start_time"));
//                   arg.putString("end_time",getArguments().getString("end_time"));
//
//
//                   switch (model.getName())
//                    {
//                        case  "Half Sangam" :
//                          fm = new HalfSangamFragment();
//                            break;
//                        case  "DP Motor" :
//                        case  "SP Motor" :
//                            fm = new SPMotor();
//                            break;
//                        case  "Cycle Pana" :
//                        fm = new CyclePana();
//                        break;
//                        case "Full Sangam" :
//                            fm = new FullSangamFragmnet();
//                            break;
//                        case "Panel Group" :
//                            fm = new GroupPanel();
//                            break;
//                        case "Group Jodi" :
//                            fm = new GroupJodi();
//                            break;
//                        case "Red Bracket": fm = new RedBracketFragment();
//                            break;
//                        case "Odd Even" : fm = new OddEvenFragment();
//                            break;
////                        case "Single Pana" :
////                         case "Double Pana":
////                            fm = new FragmentDigits();
////                            break;
//
//                        default:  fm = new SelectGameFragment();
//                        break;
//
//
//                    }
//                   fm.setArguments(arg);
//                   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                   fragmentManager.beginTransaction().replace(R.id.container_frame, fm)
//                           .addToBackStack(null).commit();
//               }
//               else
//               {
//                   Intent intent=new Intent(getActivity(),PanaActivity.class);
//                   intent.putExtra("game_id",model.getId());
//                   intent.putExtra("game_name",model.getName());
//                   intent.putExtra("m_id",getArguments().getString("m_id"));
//                   intent.putExtra("matka_name",getArguments().getString("matka_name"));
//                   intent.putExtra("start_time",getArguments().getString("start_time"));
//                   intent.putExtra("end_time",getArguments().getString("end_time"));
//                   intent.putExtra("start_num",getArguments().getString("start_num"));
//                   intent.putExtra("num",getArguments().getString("num"));
//                   intent.putExtra("end_num",getArguments().getString("end_num"));
//                   startActivity(intent);
//               }
//
//
//           }
//
//           @Override
//           public void onLongItemClick(View view, int position) {
//
//           }
//       }));


       return  view ;
    }
}
