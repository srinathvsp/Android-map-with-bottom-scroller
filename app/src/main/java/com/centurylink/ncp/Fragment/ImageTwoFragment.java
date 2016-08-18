package com.centurylink.ncp.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centurylink.ncp.R;

/**
 * Created by ab65363 on 8/3/2016.
 */
public class ImageTwoFragment extends Fragment {
    public ImageTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_two, container, false);
    }
}
