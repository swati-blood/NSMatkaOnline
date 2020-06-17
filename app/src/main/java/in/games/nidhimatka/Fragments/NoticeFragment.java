package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setTitle("Game Instructions");
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }
}