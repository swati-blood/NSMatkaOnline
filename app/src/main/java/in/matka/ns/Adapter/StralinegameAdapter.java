package in.matka.ns.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.matka.ns.Activity.PanaActivity;
import in.matka.ns.Fragments.CyclePana;
import in.matka.ns.Fragments.FullSangamFragmnet;
import in.matka.ns.Fragments.GroupJodi;
import in.matka.ns.Fragments.GroupPanel;
import in.matka.ns.Fragments.HalfSangamFragment;
import in.matka.ns.Fragments.OddEvenFragment;
import in.matka.ns.Fragments.PanaFragment;
import in.matka.ns.Fragments.RedBracketFragment;
import in.matka.ns.Fragments.SPMotor;
import in.matka.ns.Fragments.SelectGameFragment;
import in.matka.ns.Model.GameModel;
import in.matka.ns.R;

public class StralinegameAdapter extends RecyclerView.Adapter<StralinegameAdapter.ViewHolder> {
    Activity activity;
    ArrayList<GameModel> game_list;
    String matka_id ,start_time,end_time;
    Animation animation ;
    LayoutAnimationController controller;

    public StralinegameAdapter(Activity activity, ArrayList<GameModel> game_list, String matka_id, String start_time, String end_time) {
        this.activity = activity;
        this.game_list = game_list;
        this.matka_id = matka_id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @NonNull
    @Override
    public StralinegameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_games,null);
       ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StralinegameAdapter.ViewHolder holder, int position) {
        final GameModel model = game_list.get(position);
        holder.game_name.setText(model.getName());
        holder.game_img.setImageDrawable(activity.getDrawable(model.getImg()));
//       holder.lin_game.setLayoutAnimation(controller);
        holder.lin_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.lin_game.setAnimation(animation);
                holder.game_img.setAnimation(animation);
//                tv_game.setText(model.getName());
                Fragment fm = null ;
                if( model.getType().equals("0"))
                {
                    fm = new PanaFragment();

                    final Bundle arg = new Bundle();
                    arg.putString("game_id",model.getId());
                    arg.putString("game_name",model.getName());
                    arg.putString("m_id",matka_id);
//                    arg.putString("matka_name",matka_name);
                    arg.putString("start_time",start_time);
                    arg.putString("end_time",end_time);
                    fm.setArguments(arg);
                    AppCompatActivity activity=(AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,fm)
                            .addToBackStack(null)
                            .commit();

                }
                else if( model.getType().equals("1"))
                {

                    final Bundle arg = new Bundle();
                    arg.putString("game_id",model.getId());
                    arg.putString("game_name",model.getName());
                    arg.putString("m_id",matka_id);
//                    arg.putString("matka_name",matka_name);
                    arg.putString("start_time",start_time);
                    arg.putString("end_time",end_time);


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
//                        case "Single Pana" :
//                         case "Double Pana":
//                            fm = new FragmentDigits();
//                            break;

                        default:  fm = new SelectGameFragment();
                            break;


                    }
                    fm.setArguments(arg);
                    AppCompatActivity activity=(AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,fm)
                            .addToBackStack(null)
                            .commit();

                }
                else
                {
                    Intent intent=new Intent(activity, PanaActivity.class);
                    intent.putExtra("game_id",model.getId());
                    intent.putExtra("game_name",model.getName());
                    intent.putExtra("m_id",matka_id);
//                    intent.putExtra("matka_name",matka_name);
                    intent.putExtra("start_time",start_time);
                    intent.putExtra("end_time",end_time);
//                    intent.putExtra("start_num",s_num);
//                    intent.putExtra("num",num);
//                    intent.putExtra("end_num",e_num);
                    activity.startActivity(intent);
                }


            }
        });
        if(model.isIs_disable()){
            holder.itemView.setVisibility(View.VISIBLE);
        }else{
            holder.itemView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return game_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView game_img ;
        TextView game_name ;
        LinearLayout lin_game;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            game_img = itemView.findViewById(R.id.game_img);
            game_name = itemView.findViewById(R.id.game_name);
            lin_game = itemView.findViewById(R.id.lin_game);
            animation = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                    R.anim.bounce);
            controller =
                    AnimationUtils.loadLayoutAnimation(activity,R.anim.animlayout);
        }
    }
}
