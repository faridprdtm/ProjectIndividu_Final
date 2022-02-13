package com.farid.projectindividu.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.farid.projectindividu.R;
import com.farid.projectindividu.databinding.FragmentHomeBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeFragment extends Fragment implements View.OnClickListener {
//    TextInputLayout tilName;
 TextInputEditText edit_nama_home,edit_email_home,edit_hp_home;
 Button btn_input_user;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        edit_nama_home = view.findViewById(R.id.edit_name_home);
        edit_email_home=view.findViewById(R.id.edit_email_home);
        edit_hp_home = view.findViewById(R.id.edit_hp_home);
        btn_input_user = view.findViewById(R.id.btn_input_user);
//        tilName=view.findViewById(R.id.btn_input_user);
        btn_input_user.setOnClickListener(this);
        return view;
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
    }



    @Override
    public void onClick(View view) {
        if (view == btn_input_user) {
            confirmAddDataInstruktur();
        }
    }

    private void confirmAddDataInstruktur() {
        final String hom_nama = edit_nama_home.getText().toString().trim();
        final String hom_email = edit_email_home.getText().toString().trim();
        final String hom_hp = edit_hp_home.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Nama : " + hom_nama +
                "\n Email: " + hom_email +
                "\n No Hp: " + hom_hp);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edit_nama_home.setText("");
                edit_email_home.setText("");
                edit_hp_home.setText("");

            }
        }); AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}
