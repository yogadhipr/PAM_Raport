package com.example.raport;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterInput extends FragmentStateAdapter {

    public AdapterInput(@NonNull FragmentManager fragment, @NonNull Lifecycle lc) {
        super(fragment, lc);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new InputCatatan();
        }
        return new InputNilai();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
