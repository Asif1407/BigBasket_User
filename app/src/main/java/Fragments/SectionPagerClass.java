package Fragments;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerClass extends FragmentPagerAdapter {

    public SectionPagerClass(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
               VegetableFragment vegetableFragment = new VegetableFragment();
//               getPagerTitle(0);
                return vegetableFragment;
            case 1:
                FruitFragment fruitFragment = new FruitFragment();
//                getPagerTitle(1);
                return fruitFragment;
            case 2:
                OtherFragment otherFragment = new OtherFragment();
//                getPagerTitle(2);
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
