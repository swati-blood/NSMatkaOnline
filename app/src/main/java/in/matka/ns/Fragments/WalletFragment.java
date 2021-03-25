package in.matka.ns.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment implements View.OnClickListener {
    RelativeLayout cvAdd_Funds,cvWithDraw_Funds,cvFundReq_history;
    Fragment fm = null ;
    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        initViews(view);
        return  view ;
    }
    public void initViews(View v)
    {
        cvAdd_Funds=v.findViewById(R.id.cvAddFunds);
        cvWithDraw_Funds=v.findViewById(R.id.cvWithdrawFunds);
        cvFundReq_history=v.findViewById(R.id.cvFund_req_history);
        cvAdd_Funds.setOnClickListener(this);
        cvFundReq_history.setOnClickListener(this);
        cvWithDraw_Funds.setOnClickListener(this);
        ((MainActivity) getActivity()).setTitle("Funds");
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.cvAddFunds)
       {
           fm = new AddFunds();

           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
           fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                   .addToBackStack(null).commit();
       }

       else if (v.getId() ==R.id.cvWithdrawFunds)
       {
           fm = new WithdrawFundsFragment();

           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
           fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                   .addToBackStack(null).commit();
       }
    }
}
