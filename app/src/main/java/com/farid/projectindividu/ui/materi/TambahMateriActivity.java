package com.farid.projectindividu.ui.materi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.MainActivity;
import com.farid.projectindividu.R;

import java.util.HashMap;

public class TambahMateriActivity extends AppCompatActivity implements View.OnClickListener {
    EditText add_nama_mtr;
    Button btn_add_nama_mtr, btn_lihat_mtr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_materi);
        getSupportActionBar().setTitle("Tambah Materi");
        add_nama_mtr= findViewById(R.id.add_nama_mtr);
        btn_add_nama_mtr = findViewById(R.id.btn_add_nama_mtr);
        btn_lihat_mtr = findViewById(R.id.btn_lihat_mtr);

        btn_add_nama_mtr.setOnClickListener(this);
        btn_lihat_mtr.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btn_lihat_mtr) {
            System.exit(1);
        } else if (view == btn_add_nama_mtr) {
            confirmAddDataMateri();
        }
    }

    private void confirmAddDataMateri() {
        final String kom_nama = add_nama_mtr.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Nama Materi: " + kom_nama);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataMateri() {
        final String nama = add_nama_mtr.getText().toString().trim();

        class SimpanDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahMateriActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiMateri.KEY_MAT_NAMA, nama);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiMateri.URL_ADD_MTR, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahMateriActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                System.exit(1);
            }
        }
        SimpanDataMateri simpanDataMateri = new SimpanDataMateri();
        simpanDataMateri.execute();
    }
}
