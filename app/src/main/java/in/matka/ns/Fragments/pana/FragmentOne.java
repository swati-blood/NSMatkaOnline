package in.matka.ns.Fragments.pana;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import in.matka.ns.Common.Common;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import in.matka.ns.Activity.PanaActivity;
import in.matka.ns.Adapter.SigleDoublePanaAdapter;
import in.matka.ns.Intefaces.UpdateTotalBidAmount;
import in.matka.ns.Model.TableModel;
import in.matka.ns.R;
import in.matka.ns.Util.LoadingBar;

import static in.matka.ns.Activity.PanaActivity.game_names;

import static in.matka.ns.Activity.PanaActivity.total;
import static in.matka.ns.Config.Constants.*;
import static in.matka.ns.Objects.sp_input_data.doublePanna;
import static in.matka.ns.Objects.sp_input_data.singlePaana;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    RecyclerView rv_digits;
  SigleDoublePanaAdapter sigleDoublePanaAdapter;
    LoadingBar loadingBar ;
    Common common;

  List<String> single_list;
    ArrayList<TableModel> bet_list;
    int totAmt=0;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_one, container, false);
       initViews(view);
       return view ;
    }

    void initViews(View v)
    {
        rv_digits = v.findViewById(R.id.rv_digits);
        common = new Common(getActivity());
        loadingBar = new LoadingBar(getActivity());
        single_list = new ArrayList<>();
        bet_list=new ArrayList<>();

        if (game_names.equals("Single Pana"))
        {
            single_list = Arrays.asList(singlePaana);
        }
        else if (game_names.equals("Double Pana"))
        {
            single_list = Arrays.asList(doublePanna);
        }



    }

    @Override
    public void onStart() {
        super.onStart();
        bet_list.clear();

        bet_list.addAll(((PanaActivity)getActivity()).getBidList());
        if (game_names.equals("Single Pana"))
        {
            sigleDoublePanaAdapter = new SigleDoublePanaAdapter(single_list.subList(0,12),getActivity(),1,bet_list,game_names);

        }
        else if (game_names.equals("Double Pana"))
        {
            sigleDoublePanaAdapter = new SigleDoublePanaAdapter(single_list.subList(0,10),getActivity(),1,bet_list,game_names);

        }
        rv_digits.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv_digits.setAdapter(sigleDoublePanaAdapter);
        sigleDoublePanaAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver mPointReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");
            String fragPosition = intent.getStringExtra("fragment_position");
            String points = intent.getStringExtra("point");
            String beforevalue = intent.getStringExtra("beforevalue");
            String bet_type = intent.getStringExtra("bet_type");
            String position = intent.getStringExtra("position");
            if(fragPosition.equals("1")) {
                HashMap<String, String> map = new HashMap<>();
                map.put(REV_TYPE, type);
                map.put(REV_FRAG_POSITION, fragPosition);
                map.put(REV_POINTS, points);
                map.put(REV_BEFORE_VALUE, beforevalue);
                map.put(REV_BET_TYPE, bet_type);
                map.put(REV_POSITION, position);
                totAmt = ((PanaActivity) getActivity()).getTotalBidAmount();
                common.setPanaPoints(map, totAmt, bet_list, game_names,new UpdateTotalBidAmount() {
                    @Override
                    public void updateTotalBidAmount(int amt) {
                        ((PanaActivity) getActivity()).updateTotalBidAmount(amt);
                        total.setText(String.valueOf(amt));
                    }
                });

            }
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mPointReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mPointReceiver, new IntentFilter("Points"));
    }


    //    private void deleteFromList(String pnts,int pos,String beforeTextChangeValue) {
//        if(!pnts.isEmpty())
//        {
//            if(tot!=0)
//            {
//                int tx= Integer.parseInt(pnts);
//                int beforeValue=Integer.parseInt(beforeTextChangeValue);
//                Log.e("beforeValue",""+beforeTextChangeValue+" - Next Value - " + tx);
//                Log.e("leeeeeee",""+pnts.length());
//
//                if(pnts.length()==1)
//                {
//                    tot = (tot)-beforeValue;
//                }
//                else if(pnts.length()==2)
//                {
//                    tot = (tot+tx)-beforeValue;
//                }
//                else if(pnts.length() == 3)
//                {
//                    tot = (tot+tx)-beforeValue;
//                }
//                else if(pnts.length()==4)
//                {
//
//                    tot = (tot+tx)-beforeValue;
//                }
//                else if(pnts.length()==5)
//                {
//
//                    tot = (tot+tx)-beforeValue;
//                }
//
//                total.setText(String.valueOf(tot));
////                ponitsList.set(pos,"0");
//                if (txt_type.getText().toString().equals(activity.getResources().getString(R.string.select_type)))
//                {
////                                    Toast.makeText(activity, "Select game type", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    if(pnts.length()>1)
//                    {
//                        common.updatePoints(bet_list,pos,pnts,txt_type.getText().toString());
//                    }else
//                    {
//                        common.updatePoints(bet_list,pos,"0",txt_type.getText().toString());
//                    }
//
////
//                }
//
//
//            }
//        }
//
//    }

//    private void addToBetList(String points,int pos) {
//
//        if (points.length() != 0) {
//
//            if (points.isEmpty()) {
//
//                }
//                else {
//                    int ps = Integer.parseInt(points);
//                    if(points.length()==2)
//                    {
//                        Log.e("digits2",""+points);
//                        tot = tot + ps;
//                    }
//                    else if(points.length() == 3)
//                    {
//                        tot = (tot + ps)-Integer.parseInt(bet_list.get(pos).getPoints());
//                        Log.e("digits3",""+points);
//                    }
//                    else if(points.length()==4)
//                    {
//                        tot = (tot + ps)-Integer.parseInt(bet_list.get(pos).getPoints());
//
//                        Log.e("digits4",""+points);
//                    }
//                    else if(points.length()==5)
//                    {
//                        tot = (tot + ps)-Integer.parseInt(bet_list.get(pos).getPoints());
//
//                        Log.e("digits4",""+points);
//                    }
//
//                    is_empty = false;
//                    is_error = false;
//                    total.setText(String.valueOf(tot));
//                    if (txt_type.getText().toString().equals(activity.getResources().getString(R.string.select_type)))
//                    {
////                                    Toast.makeText(activity, "Select game type", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        common.updatePoints(bet_list,pos,points,txt_type.getText().toString());
////                                    bet_list.add(new TableModel(digit_list.get(i), points, txt_type.getText().toString()));
//                    }
//                }
//            }
//        }
//    }
//    private void removeBidToList(String pnts, String bet_type, int pos, String beforevalue) {
//        if(!pnts.isEmpty())
//        {
//            if(tot!=0)
//            {
//                int tx= Integer.parseInt(pnts);
//                int beforeValue=Integer.parseInt(beforevalue);
//
//                if(pnts.length()==1)
//                {
//                    tot = (tot)-beforeValue;
//                }
//                else if(pnts.length()==2)
//                {
//                    tot = (tot+tx)-beforeValue;
//                }
//                else if(pnts.length() == 3)
//                {
//                    tot = (tot+tx)-beforeValue;
//                }
//                else if(pnts.length()==4)
//                {
//
//                    tot = (tot+tx)-beforeValue;
//                }
//                else if(pnts.length()==5)
//                {
//
//                    tot = (tot+tx)-beforeValue;
//                }
//
//                total.setText(String.valueOf(tot));
////                ponitsList.set(pos,"0");
//                if (bet_type.toString().equals(getActivity().getResources().getString(R.string.select_type)))
//                {
////                                    Toast.makeText(activity, "Select game type", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    if(pnts.length()>1)
//                    {
//                        common.updatePoints(bet_list,pos,pnts,bet_type.toString());
//                    }else
//                    {
//                        common.updatePoints(bet_list,pos,"0",bet_type.toString());
//                    }
//
////
//                }
//
//
//            }
//        }
//
//    }

//    private void addBidToList(String points, String bet_type, int position, String beforevalue) {
//        if (points.length() != 0) {
//
//            if (points.isEmpty()) {
//
//            }
//            else {
//                int ps = Integer.parseInt(points);
//                if(points.length()==2)
//                {
//                    Log.e("digits2",""+points);
//                    tot = tot + ps;
//                }
//                else if(points.length() == 3)
//                {
//                    tot = (tot + ps)-Integer.parseInt(beforevalue);
//                    Log.e("digits3",""+points);
//                }
//                else if(points.length()==4)
//                {
//                    tot = (tot + ps)-Integer.parseInt(beforevalue);
//
//                    Log.e("digits4",""+points);
//                }
//                else if(points.length()==5)
//                {
//                    tot = (tot + ps)-Integer.parseInt(beforevalue);
//
//                    Log.e("digits4",""+points);
//                }
//                ((PanaActivity)getActivity()).updateTotalBidAmount(tot);
//                total.setText(String.valueOf(tot));
//                if (bet_type.toString().equals(getActivity().getResources().getString(R.string.select_type)))
//                {
//                }
//                else {
//                    common.updatePoints(bet_list,position,points,bet_type.toString());
//                }
//            }
//        }
//    }

}
