package com.farid.projectindividu.ui.instruktur;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class TambahInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText add_nama_ins,add_email_ins,add_hp_ins;
    Button btn_add_instruktur,btn_lihat_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_instruktur);
        getSupportActionBar().setTitle("Tambah Instruktur");

        add_nama_ins = findViewById(R.id.add_nama_ins);
        add_email_ins = findViewById(R.id.add_email_ins);
        add_hp_ins = findViewById(R.id.add_hp_ins);
        btn_add_instruktur = findViewById(R.id.btn_add_instruktur);
        btn_lihat_instruktur = findViewById(R.id.btn_lihat_instruktur);

        btn_lihat_instruktur.setOnClickListener(this);
        btn_add_instruktur.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_add_instruktur) {
            confirmAddDataInstruktur();
        } else if (view == btn_lihat_instruktur) {
            System.exit(1);
        }
    }

    private void confirmAddDataInstruktur() {
        final String kom_nama = add_nama_ins.getText().toString().trim();
        final String kom_email = add_email_ins.getText().toString().trim();
        final String kom_hp_ins = add_hp_ins.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Nama : " + kom_nama +
                "\n Email: " + kom_email +
                "\n No Hp: " + kom_hp_ins);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataInstruktur() {

            final String nama = add_nama_ins.getText().toString().trim();
            final String email = add_email_ins.getText().toString().trim();
            final String hp_ins = add_hp_ins.getText().toString().trim();

            class SimpanDataInstruktur extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(TambahInstrukturActivity.this,
                            "Menyimpan Data", "Harap tunggu...",
                            false, false);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(KonfigurasiInstruktur.KEY_INS_NAMA, nama);
                    params.put(KonfigurasiInstruktur.KEY_INS_EMAIL, email);
                    params.put(KonfigurasiInstruktur.KEY_INS_HP, hp_ins);
                    HttpHandler handler = new HttpHandler();
                    String result = handler.sendPostRequest(KonfigurasiInstruktur.URL_ADD, params);
                    //System.out.println("Result" + params);
                    return result;
                }

                @Override
                protected void onPostExecute(String message) {
                    super.onPostExecute(message);
                    loading.dismiss();
                    System.exit(1);
                }
            }
            SimpanDataInstruktur simpanDataInstruktur = new SimpanDataInstruktur();
            simpanDataInstruktur.execute();
        }
    }
