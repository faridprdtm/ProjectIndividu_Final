package com.farid.projectindividu.ui.peserta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.MainActivity;
import com.farid.projectindividu.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

public class LihatDetailPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id, edit_nama, edit_email, edit_hp, edit_instansi;
    Button btn_update_peserta, btn_delete_peserta;
    String id_pst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_peserta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Data Peserta");

        edit_id = findViewById(R.id.edit_id);
        edit_nama = findViewById(R.id.edit_nama);
        edit_email = findViewById(R.id.edit_email);
        edit_hp = findViewById(R.id.edit_hp);
        edit_instansi = findViewById(R.id.edit_instansi);
        btn_update_peserta = findViewById(R.id.btn_update_peserta);
        btn_delete_peserta = findViewById(R.id.btn_delete_peserta);

        //menerima intent dari class LihatDetailPesertaActivity
        Intent receiveIntent = getIntent();
        id_pst= receiveIntent.getStringExtra(Konfigurasi.PST_ID);
        edit_id.setText(id_pst);

        //mengambil data JSON
        getJSON();
        //button
        btn_update_peserta.setOnClickListener(this);
        btn_delete_peserta.setOnClickListener(this);
    }

    private void getJSON() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailPesertaActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_PST, id_pst);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
//                Toast.makeText(LihatDetailPesertaActivity.this, "Data " + message,
//                        Toast.LENGTH_SHORT).show();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_pst = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
            String email_pst = object.getString(Konfigurasi.TAG_JSON_EMAIL_PST);
            String hp_pst = object.getString(Konfigurasi.TAG_JSON_HP_PST);
            String instansi_pst = object.getString(Konfigurasi.TAG_JSON_INSTANSI_PST);


            edit_nama.setText(nama_pst);
            edit_email.setText(email_pst);
            edit_hp.setText(hp_pst);
            edit_instansi.setText(instansi_pst);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View myButton) {
        if (myButton == btn_update_peserta)
        {
            updateDataPeserta();
        }
        else if (myButton == btn_delete_peserta)
        {
            confirmDeleteDataPeserta();
        }
    }

    private void confirmDeleteDataPeserta() {
        //Confirmation alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Yakin mau hapus ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDataPeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataPeserta() {
        class Delete extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailPesertaActivity.this,
                        "Deleting Data", "Please Wait...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE_PST, id_pst);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(LihatDetailPesertaActivity.this, "pesan :" +message,
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LihatDetailPesertaActivity.this, MainActivity.class));
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }

    private void updateDataPeserta() {
        // data apa saja yang akan diubah
        final String nama_pst = edit_nama.getText().toString().trim();
        final String email_pst = edit_email.getText().toString().trim();
        final String hp_pst = edit_hp.getText().toString().trim();
        final String instansi_pst = edit_instansi.getText().toString().trim();

        class UpdateDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailPesertaActivity.this,
                        "Mengubah data", "Harap tunggu..", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_PST_ID, id_pst);
                params.put(Konfigurasi.KEY_PST_NAMA, nama_pst);
                params.put(Konfigurasi.KEY_PST_EMAIL, email_pst);
                params.put(Konfigurasi.KEY_PST_HP, hp_pst);
                params.put(Konfigurasi.KEY_PST_INSTANSI, instansi_pst);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_PST, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(LihatDetailPesertaActivity.this,
                        "pesan :" +message , Toast.LENGTH_SHORT).show();
                //redirect ke LihatDataActivity
                startActivity(new Intent(LihatDetailPesertaActivity.this, MainActivity.class));
            }
        }
        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }
}
