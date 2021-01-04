package Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionPagerClass extends FragmentStatePagerAdapter {

    // I just updated FragmentPagerAdapter to FragmentStatePagerAdapter and Tabs start Working Properly.

    public SectionPagerClass(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new VegetableFragment();
            case 1:
                return new FruitFragment();
            case 2:
                return new OtherFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch(position){
            case 0:
                return "Vegetables";
            case 1:
                return "Fruits";
            case 2:
                return "Others";
            default:
                return null;
        }
    }
}
