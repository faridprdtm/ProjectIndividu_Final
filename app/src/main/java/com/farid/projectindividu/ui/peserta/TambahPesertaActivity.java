package com.farid.projectindividu.ui.peserta;

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

public class TambahPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_add_data_pst,btn_lihat_pst;
    EditText form_nama_pst,form_email_pst,form_hp_pst,form_instansi_pst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta);

        btn_add_data_pst = findViewById(R.id.btn_add_data_pst);
        btn_lihat_pst = findViewById(R.id.btn_lihat_pst);
        form_nama_pst = findViewById(R.id.form_nama_pst);
        form_email_pst = findViewById(R.id.form_email_pst);
        form_hp_pst = findViewById(R.id.form_hp_pst);
        form_instansi_pst = findViewById(R.id.form_ins_pst);

        btn_lihat_pst.setOnClickListener(this);
        btn_add_data_pst.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_lihat_pst){
            Intent intent =new Intent(TambahPesertaActivity.this, PesertaFragment.class);
            startActivity(intent);
        }else if (view == btn_add_data_pst){
            confirmAddPeserta();
        }
    }

    private void confirmAddPeserta() {
        final String form_nama =form_nama_pst.getText().toString().trim();
        final String form_email = form_email_pst.getText().toString().trim();
        final  String form_hp = form_hp_pst.getText().toString().trim();
        final String form_ins_pst = form_instansi_pst.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data Peserta");
        builder.setMessage("Apakah Anda yakin? \n" +
                "\n Nama    :" + form_nama +
                "\n Email   :" + form_email +
                "\n HP      :" + form_hp +
                "\n Instansi:" +form_ins_pst);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataPeserta();
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void simpanDataPeserta() {
        final String nama_pst =form_nama_pst.getText().toString().trim();
        final String email_pst = form_email_pst.getText().toString().trim();
        final  String hp_pst = form_hp_pst.getText().toString().trim();
        final String instansi_pst = form_instansi_pst.getText().toString().trim();

        class SimpanDataPeserta extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show( TambahPesertaActivity.this,
                        "Menyimpan Data", "Harap Tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PST_NAMA, nama_pst);
                params.put(Konfigurasi.KEY_PST_EMAIL, email_pst);
                params.put(Konfigurasi.KEY_PST_HP, hp_pst);
                params.put(Konfigurasi.KEY_PST_INSTANSI, instansi_pst);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_PST, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahPesertaActivity.this,
                        "pesan :" +message, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(TambahPesertaActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        }
        SimpanDataPeserta simpanDataPeserta =new SimpanDataPeserta();
        simpanDataPeserta.execute();
    }
//    private void clearText() {
//        form_nama_pst.setText("");
//        form_email_pst.setText("");
//        form_hp_pst.setText("");
//        form_instansi_pst.setText("");
//        form_nama_pst.requestFocus();
//    }
}