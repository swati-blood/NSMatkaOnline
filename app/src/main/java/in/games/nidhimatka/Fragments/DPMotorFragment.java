package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.games.nidhimatka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DPMotorFragment extends Fragment {

    public DPMotorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_d_p_motor, container, false);
    }
}
