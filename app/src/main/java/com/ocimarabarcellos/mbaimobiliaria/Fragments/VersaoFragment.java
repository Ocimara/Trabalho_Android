package com.ocimarabarcellos.mbaimobiliaria.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ocimarabarcellos.mbaimobiliaria.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VersaoFragment extends Fragment {


    public VersaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_versao, container, false);
    }

}
