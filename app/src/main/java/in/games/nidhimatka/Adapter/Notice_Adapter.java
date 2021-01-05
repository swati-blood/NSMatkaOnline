package in.games.nidhimatka.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.games.nidhimatka.Fragments.NoticeFragment;
import in.games.nidhimatka.Model.Notice_Model;
import in.games.nidhimatka.R;

public class Notice_Adapter extends RecyclerView.Adapter<Notice_Adapter.ViewHolder>{

    NoticeFragment context;
    Notice_Model[] data;

    public Notice_Adapter(Notice_Model[]data, NoticeFragment activity){
        this.data=data;
        this.context=activity;
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
        final Notice_Model datalist=data[position];
        holder.title.setText (datalist.getTitle ());
        holder.detail.setText (datalist.getDetail());
    }

    @Override
    public int getItemCount() {
        return data.length;
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
