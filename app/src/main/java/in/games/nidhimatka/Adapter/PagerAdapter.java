package in.games.nidhimatka.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import in.games.nidhimatka.Fragments.pana.FragmentOne;
import in.games.nidhimatka.Fragments.pana.FragmentThree;
import in.games.nidhimatka.Fragments.pana.FragmentTwo;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 24,June,2020
 */
public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        mNumOfTabs=noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                FragmentOne fm1=new FragmentOne();
                return fm1;
            case 1:
                FragmentTwo fm2=new FragmentTwo();
                return fm2;
            case 2:
                FragmentThree fm3=new FragmentThree();
                return fm3;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;

    }
}
