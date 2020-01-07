package com.sam007.samiyal.reg_app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam007.samiyal.reg_app.R;

public class SettingsFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_settings,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return myView;
    }
}
