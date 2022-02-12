package com.farid.projectindividu.ui.materi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.farid.projectindividu.R;
import com.farid.projectindividu.ui.peserta.Konfigurasi;
import com.farid.projectindividu.ui.peserta.LihatDetailPesertaActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LihatDetailMateriActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_id_mtr, edit_nama_mtr;
    Button btn_update_mtr,btn_delete_mtr;
    String id_mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_materi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Data Materi");

        edit_id_mtr = findViewById(R.id.edit_id_mtr);
        edit_nama_mtr = findViewById(R.id.edit_nama_mtr);
        btn_update_mtr = findViewById(R.id.btn_update_mtr);
        btn_delete_mtr = findViewById(R.id.btn_delete_mtr);

        Intent receiveIntent = getIntent();
        id_mat=receiveIntent.getStringExtra(KonfigurasiMateri.MAT_ID);
        edit_id_mtr.setText(id_mat);

        getJSON();

        btn_update_mtr.setOnClickListener(this);
        btn_delete_mtr.setOnClickListener(this);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailMateriActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_GET_DETAIL_MTR, id_mat);
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

            String nama_pst = object.getString(KonfigurasiMateri.TAG_JSON_NAMA);
            edit_nama_mtr.setText(nama_pst);

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
        if (myButton == btn_update_mtr)
        {
            updateDataMateri();
        }
        else if (myButton == btn_delete_mtr)
        {
            confirmDeleteDataMateri();
        }
    }

    private void confirmDeleteDataMateri() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Are you sure want to delete this data?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataMateri() {
        class DeleteDataMateri extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(LihatDetailMateriActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_DELETE_MTR, id_mat);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(LihatDetailMateriActivity.this,"Pesan: " + s,
                        Toast.LENGTH_LONG).show();
                System.exit(1);

            }
        }
        DeleteDataMateri deleteDataMateri = new DeleteDataMateri();
        deleteDataMateri.execute();
    }
    private void updateDataMateri() {
        // variable data pegawai yang akan diubah
        final String nama_mat = edit_nama_mtr.getText().toString().trim();

        class UpdateDataMateri extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailMateriActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiMateri.KEY_MAT_ID,id_mat);
                params.put(KonfigurasiMateri.KEY_MAT_NAMA,nama_mat);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiMateri.URL_UPDATE_MTR, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;
                Fragment fragment = null;
                Toast.makeText(LihatDetailMateriActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                System.exit(1);

            }
        }
        UpdateDataMateri updateDataMateri = new UpdateDataMateri();
        updateDataMateri.execute();
    }
}
