package in.games.rosegame.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.games.rosegame.R;

public class RecyclerPagerAdapter extends RecyclerView.Adapter<RecyclerPagerAdapter.ViewHodler> {
    List<String> digit_list ;
    Activity activity;
    List<String> list ;
  String type ;
  TextView tv_total ;

    public RecyclerPagerAdapter(List<String> digit_list, Activity activity, String type ,TextView tv_total) {
        this.digit_list = digit_list;
        this.activity = activity;
        this.type = type;
        this.tv_total = tv_total;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_list_pager,null);
        ViewHodler holder = new ViewHodler(view);
        return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {

        if (position==0)
        {
            list=digit_list.subList(0,10);
        }
       else if (position==1)
        {
            list= digit_list.subList(10,20);
        }
       else if (position==2)
        {
            list=digit_list.subList(20,30);
        }
      else  if (position==3)
        {
            list=digit_list.subList(30,40);
        }
       else if (position==4)
        {
            list=digit_list.subList(40,50);
        }
       else if (position==5)
        {
            list=digit_list.subList(50,60);
        }
       else if (position==6)
        {
            list=digit_list.subList(60,70);
        }
       else  if (position==7)
        {
            list=digit_list.subList(70,80);
        }
       else  if (position==8)
        {
            list=digit_list.subList(80,90);
        }
       else  if (position==9)
        {
            list=digit_list.subList(90,digit_list.size()-1);
        }
    PointsAdapter pointsAdapter = new PointsAdapter(list,activity);
    holder.rv_digits.setLayoutManager(new GridLayoutManager(activity,2));
    holder.rv_digits.setAdapter(pointsAdapter);
    pointsAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
//        int no = digit_list.size()/10;
//        int r = digit_list.size()%10;
//        if (r == 0)
//        {
//            return no;
//        }
//        else if (r > 0)
//        {
//            return no+1;
//        }
//        else {
//        return 10;
//        }
        return 10;
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        RecyclerView rv_digits ;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            rv_digits = itemView.findViewById(R.id.rv_digits);
            list = new ArrayList<>();
        }
    }

    public void getlist( int s , int e)
    {  for (int i = s;i<e ; i++)
    {
        int index= 0;
        list.add(index,digit_list.get(i));
        index ++;
    }}
}
