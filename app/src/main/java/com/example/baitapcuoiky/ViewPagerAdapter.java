package com.example.baitapcuoiky;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baitapcuoiky.fragment.DoneFragment;
import com.example.baitapcuoiky.fragment.NotDoneFragment;
import com.example.baitapcuoiky.model.User;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NotDoneFragment();
            case 1:
                return new DoneFragment();
            default:
                return new NotDoneFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
