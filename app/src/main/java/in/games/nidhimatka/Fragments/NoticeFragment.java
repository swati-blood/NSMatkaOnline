package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.Notice_Adapter;
import in.games.nidhimatka.Model.Notice_Model;
import in.games.nidhimatka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {
RecyclerView recyclerView;
    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setTitle("Game Instructions");


        View view=inflater.inflate(R.layout.fragment_notice, container, false);
        recyclerView=view.findViewById (R.id.recyclerView);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager(view.getContext ()));
        Notice_Model[]data=new Notice_Model[]{

                new Notice_Model ("Withdraw", "Withdraw will be accepted from 10:00am till 2:00pm and amount will be credited within 24hrs. Withdrawal is not available on Sunday and on Bank Holidays.\\n For Funds Withdrawal Query Contact\""),
                new Notice_Model ("Notice", "All customers are requested to update your Bank Account with IFSC code in your Profile before remittnaces. It's mandatory kindly cooperate"),
        };



        Notice_Adapter notice_adapter=new Notice_Adapter (data,NoticeFragment.this);
        recyclerView.setAdapter (notice_adapter);
        return  view;
    }

}
