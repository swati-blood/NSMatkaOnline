package in.matka.rose.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import in.matka.rose.Fragments.pana.FragementSeven;
import in.matka.rose.Fragments.pana.FragemntTen;
import in.matka.rose.Fragments.pana.FragmentEight;
import in.matka.rose.Fragments.pana.FragmentFive;
import in.matka.rose.Fragments.pana.FragmentNine;
import in.matka.rose.Fragments.pana.FragmentOne;
import in.matka.rose.Fragments.pana.FragmentSix;
import in.matka.rose.Fragments.pana.FragmentThree;
import in.matka.rose.Fragments.pana.FragmentTwo;
import in.matka.rose.Fragments.pana.Fragmenteleven;
import in.matka.rose.Fragments.pana.Fragmentfour;

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
            case 3 :
                Fragmentfour fm4 = new Fragmentfour();
                return fm4;
            case 4 :
                FragmentFive fm5 = new FragmentFive();
                return fm5;
            case 5 :
                FragmentSix fm6 = new FragmentSix();
                return fm6;
            case 6:
                FragementSeven fm7 = new FragementSeven();
                return  fm7;
            case 7 :
                FragmentEight fm8 = new FragmentEight();
                return  fm8;
            case 8 :
                FragmentNine fm9 = new FragmentNine();
                return fm9;
            case 9 :
                FragemntTen fm10= new FragemntTen();
                return  fm10;
            case 10 :
                Fragmenteleven fm11= new Fragmenteleven();
                return  fm11;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;

    }
}
