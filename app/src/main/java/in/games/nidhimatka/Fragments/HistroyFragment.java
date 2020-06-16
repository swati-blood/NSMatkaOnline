package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import in.games.nidhimatka.R;

public class HistroyFragment extends Fragment implements View.OnClickListener {
   RelativeLayout cvBid_history,cv_Fund_req_history,cv_Trans_hitory,cv_Starline_history,cv_Withdraw_req;
   Fragment fm = null;

    public HistroyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_histroy, container, false);
       initViews(view);
       return view ;

    }
    public void initViews(View v)
    {
        cvBid_history=v.findViewById(R.id.cvBidHistory);
        cv_Fund_req_history=v.findViewById(R.id.cvFund_req_history);
        cv_Trans_hitory=v.findViewById(R.id.cvTrans_history);
        cv_Starline_history=v.findViewById(R.id.cvGoogle);
        cv_Withdraw_req=v.findViewById(R.id.cvWithdraw_req);
        cv_Fund_req_history.setOnClickListener(this);
        cvBid_history.setOnClickListener(this);
        cv_Trans_hitory.setOnClickListener(this);
        cv_Withdraw_req.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();

        if(v.getId() == R.id.cvFund_req_history)
        {
            bundle.putString("type","funds");
            fm = new AllHistryListFragment();
            fm.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (v.getId() == R.id.cvBidHistory)
        {
            fm = new MatkaListFragment();
            fm.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (v.getId() == R.id.cvTrans_history)
        {
            bundle.putString("type","transaction");
            fm = new AllHistryListFragment();
            fm.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
        else if (v.getId() == R.id.cvWithdraw_req)
        {
            bundle.putString("type","withdraw");
            fm = new AllHistryListFragment();
            fm.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }
    }
}
