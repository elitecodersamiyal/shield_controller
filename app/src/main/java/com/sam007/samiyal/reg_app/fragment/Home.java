package com.sam007.samiyal.reg_app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam007.samiyal.reg_app.R;

public class Home extends Fragment {

    View myView;
   // Button golapp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home,container,false);
        /*golapp = myView.findViewById(R.id.golockapp);
        golapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fman = getFragmentManager();
                fman.beginTransaction().replace(R.id.content_frame,new SettingsFragment()).commit();
            }
        });*/
        //return super.onCreateView(inflater, container, savedInstanceState);
        return myView;
    }

}
