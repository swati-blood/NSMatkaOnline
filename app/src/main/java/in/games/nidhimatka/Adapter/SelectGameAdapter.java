package in.games.nidhimatka.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.games.nidhimatka.Model.GameModel;
import in.games.nidhimatka.R;

public class SelectGameAdapter extends RecyclerView.Adapter<SelectGameAdapter.ViewHolder> {
    Activity activity;
    ArrayList<GameModel> game_list;

    public SelectGameAdapter(Activity activity, ArrayList<GameModel> game_list) {
        this.activity = activity;
        this.game_list = game_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(activity).inflate(R.layout.row_games,null);
     ViewHolder holder = new ViewHolder(view);
     return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameModel model = game_list.get(position);
       holder.game_name.setText(model.getName());
       holder.game_img.setImageDrawable(activity.getDrawable(R.drawable.cyclepana));


    }

    @Override
    public int getItemCount() {
        return game_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView game_img ;
        TextView game_name ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            game_img = itemView.findViewById(R.id.game_img);
            game_name = itemView.findViewById(R.id.game_name);
        }
    }
}
