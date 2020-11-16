package Fragments;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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
               VegetableFragment vegetableFragment = new VegetableFragment();
                Log.i("VegetableFrag","Fragment Changed To Vegetables");
                return vegetableFragment;
            case 1:
                FruitFragment fruitFragment = new FruitFragment();
                Log.i("FruitsFrag","Fragment Changed To Fruits");
                return fruitFragment;
            case 2:
                OtherFragment otherFragment = new OtherFragment();
                Log.i("OtherFrag","Fragment Changed To Others");
                return  otherFragment;
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
