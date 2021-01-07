package in.games.rosegame.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.games.rosegame.Fragments.NoticeFragment;
import in.games.rosegame.Model.Notice_Model;
import in.games.rosegame.R;

public class Notice_Adapter extends RecyclerView.Adapter<Notice_Adapter.ViewHolder>{

    NoticeFragment context;
    ArrayList<Notice_Model> data;

    public Notice_Adapter(NoticeFragment context, ArrayList<Notice_Model> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Notice_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from (parent.getContext ());
        View view=layoutInflater.inflate (R.layout.notice_item,parent,false);
        ViewHolder viewHolder=new ViewHolder (view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull Notice_Adapter.ViewHolder holder, int position) {
        final Notice_Model datalist=data.get (position);
        holder.title.setText (datalist.getTitle ());
        holder.detail.setText (datalist.getDescription ());
    }

    @Override
    public int getItemCount() {
        return data.size ();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView title;
        TextView detail;


        public ViewHolder(@NonNull View itemView) {

            super (itemView);
            title=itemView.findViewById (R.id.title);
            detail=itemView.findViewById (R.id.detail);

        }
    }
}
