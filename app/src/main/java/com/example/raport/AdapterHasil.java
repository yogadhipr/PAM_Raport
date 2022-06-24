package com.example.raport;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterHasil extends FragmentStateAdapter {

    public AdapterHasil(@NonNull FragmentManager fragment, @NonNull Lifecycle lc) {
        super(fragment, lc);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new HasilCatatan();
        }
        return new HasilNilai();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
