package com.farid.projectindividu.ui.kelas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farid.projectindividu.R;


public class KelasFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelas, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}