package com.ocimarabarcellos.mbaimobiliaria.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ocimarabarcellos.mbaimobiliaria.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VersaoFragment extends Fragment {


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public VersaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_versao, container, false);


        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        final TextView tv = (TextView)getActivity().findViewById(R.id.tvCall);
        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int permissionCheck = ContextCompat.checkSelfPermission(getView().getContext(), Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    callPhone();
                }

            }
        });
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
            }
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:988878482"));
        startActivity(intent);
    }

}
