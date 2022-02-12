package com.farid.projectindividu.ui.detailkelas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farid.projectindividu.R;


public class DetailkelasFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_kelas, container, false);
        return view ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}