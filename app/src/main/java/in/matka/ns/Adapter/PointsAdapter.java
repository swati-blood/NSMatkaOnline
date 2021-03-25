package in.matka.ns.Adapter;

import android.app.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.matka.ns.Common.Common;
import in.matka.ns.Model.TableModel;
import in.matka.ns.R;

import static in.matka.ns.Fragments.PanaFragment.bet_list;
import static in.matka.ns.Fragments.PanaFragment.total;
import static in.matka.ns.Fragments.PanaFragment.txt_type;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

  public static  ArrayList<TableModel> b_list ;
 List<String> digit_list ;
    Activity activity;
public static ArrayList<String> ponitsList;
Common common;

    int tot = 0;
    int index =0 ;
    String beforeTextChangeValue="";


public static Boolean is_empty = true , is_error = false ;

    public PointsAdapter(List<String> digit_list, Activity activity) {
        this.digit_list = digit_list;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_new_digits,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.txt_digits.setText(digit_list.get(i));
        bet_list.clear();
        for(int j=0; j<digit_list.size();j++)
        {
            ponitsList.add("0");
            bet_list.add(new TableModel(digit_list.get(j).toString(),"0",txt_type.getText().toString()));
        }

        viewHolder.et_points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               beforeTextChangeValue=s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txt_type.getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.select_type))) {
                       common.showToast("Select Bid Type");
                } else {
                    boolean backSpace = false;
                    if (beforeTextChangeValue.length() > s.toString().length()) {
                        backSpace = true;
                    }

                    if (backSpace) {
                        String pnts = s.toString();
                        deleteFromList(pnts, i, beforeTextChangeValue);
                    } else {
                        String points = s.toString();
                        addToBetList(points, i);
                    }


                }
            }

        });
//

    }

    private void deleteFromList(String pnts,int pos,String beforeTextChangeValue) {
        if(!pnts.isEmpty())
        {
            if(tot!=0)
            {
                int tx= Integer.parseInt(pnts);
                int beforeValue=Integer.parseInt(beforeTextChangeValue);
                Log.e("beforeValue",""+beforeTextChangeValue+" - Next Value - " + tx);
                Log.e("leeeeeee",""+pnts.length());

                if(pnts.length()==1)
                {
                    tot = (tot)-beforeValue;
                }
                else if(pnts.length()==2)
                {
                    tot = (tot+tx)-beforeValue;
                }
                else if(pnts.length() == 3)
                {
                    tot = (tot+tx)-beforeValue;
                }
                else if(pnts.length()==4)
                {

                    tot = (tot+tx)-beforeValue;
                }
                else if(pnts.length()==5)
                {

                    tot = (tot+tx)-beforeValue;
                }

                total.setText(String.valueOf(tot));
                ponitsList.set(pos,"0");
                if (txt_type.getText().toString().equals(activity.getResources().getString(R.string.select_type)))
                {
//                                    Toast.makeText(activity, "Select game type", Toast.LENGTH_LONG).show();
                }
                else {
                    if(pnts.length()>1)
                    {
                        common.updatePoints(bet_list,pos,pnts,txt_type.getText().toString());
                    }else
                    {
                        common.updatePoints(bet_list,pos,"0",txt_type.getText().toString());
                    }

//
                }


            }
        }

    }

    private void addToBetList(String points,int pos) {
        int p =0;
        if(!points.isEmpty())
        {
            p = Integer.parseInt(points);

        }

        if (points.length() != 0) {

            if (points.isEmpty()) {
                is_empty = true;
            } else {
                is_empty = false;
                int pints = Integer.parseInt(points);
                if ( pints < 10) {
                    if(tot==0)
                    {
                        is_error = true;
                    }

                }
                else {
                    int ps = Integer.parseInt(points);
                    if(points.length()==2)
                    {
                        Log.e("digits2",""+points);
                        tot = tot + ps;
                    }
                    else if(points.length() == 3)
                    {
                        tot = (tot + ps)-Integer.parseInt(bet_list.get(pos).getPoints());
                        Log.e("digits3",""+points);
                    }
                    else if(points.length()==4)
                    {
                        tot = (tot + ps)-Integer.parseInt(bet_list.get(pos).getPoints());

                        Log.e("digits4",""+points);
                    }
                    else if(points.length()==5)
                    {
                        tot = (tot + ps)-Integer.parseInt(bet_list.get(pos).getPoints());

                        Log.e("digits4",""+points);
                    }

                    is_empty = false;
                    is_error = false;
                    ponitsList.set(pos,String.valueOf(ps));
                    total.setText(String.valueOf(tot));
                    if (txt_type.getText().toString().equals(activity.getResources().getString(R.string.select_type)))
                    {
//                                    Toast.makeText(activity, "Select game type", Toast.LENGTH_LONG).show();
                    }
                    else {
                        common.updatePoints(bet_list,pos,points,txt_type.getText().toString());
//                                    bet_list.add(new TableModel(digit_list.get(i), points, txt_type.getText().toString()));
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return digit_list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_digits ;
        EditText et_points;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ponitsList=new ArrayList<>();
            txt_digits = itemView.findViewById(R.id.txt_digit);
            et_points = itemView.findViewById(R.id.et_points);
            b_list = new ArrayList<>();
            common=new Common(activity);

        }
    }

    public static ArrayList<String> getPonitsList()
    {
        return ponitsList;
    }
    public static ArrayList<TableModel> getBetlist()
    {
        return b_list;
    }
}
