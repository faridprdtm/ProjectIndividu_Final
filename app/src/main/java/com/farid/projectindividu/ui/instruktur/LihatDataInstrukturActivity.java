package com.farid.projectindividu.ui.instruktur;

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
import android.widget.Toast;

import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LihatDataInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText edit_id_ins,edit_nama_ins,edit_email_ins,edit_hp_ins;
    Button btn_update_instruktur,btn_delete_instruktur;
    String id_ins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_instruktur);
        getSupportActionBar().setTitle("Detail Instruktur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_id_ins = findViewById(R.id.edit_id_ins);
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_email_ins = findViewById(R.id.edit_email_ins);
        edit_hp_ins = findViewById(R.id.edit_hp_ins);

        btn_update_instruktur = findViewById(R.id.btn_update_instruktur);
        btn_delete_instruktur = findViewById(R.id.btn_delete_instruktur);

        Intent receiveIntent = getIntent();
        id_ins = receiveIntent.getStringExtra(KonfigurasiInstruktur.INS_ID);
        edit_id_ins.setText(id_ins);

        btn_update_instruktur.setOnClickListener(this);
        btn_delete_instruktur.setOnClickListener(this);

        getJSON();
    }
    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataInstrukturActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiInstruktur.URL_GET_DETAIL,id_ins);
                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiInstruktur.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_NAMA);
            String email = object.getString(KonfigurasiInstruktur.TAG_JSON_EMAIL);
            String hp_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_HP);

            edit_nama_ins.setText(nama_ins);
            edit_email_ins.setText(email);
            edit_hp_ins.setText(hp_ins);
        } catch (Exception ex){
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
        if(myButton == btn_update_instruktur){
            updateDataInstruktur();
        } else if(myButton == btn_delete_instruktur){
            confirmDeleteDataInstruktur();
        }

    }

    private void updateDataInstruktur() {
        // variable data pegawai yang akan diubah
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String email = edit_email_ins.getText().toString().trim();
        final String hp_ins = edit_hp_ins.getText().toString().trim();

        class UpdateDataInstruktur extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataInstrukturActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiInstruktur.KEY_INS_ID,id_ins);
                params.put(KonfigurasiInstruktur.KEY_INS_NAMA,nama_ins);
                params.put(KonfigurasiInstruktur.KEY_INS_EMAIL,email);
                params.put(KonfigurasiInstruktur.KEY_INS_HP,hp_ins);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiInstruktur.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(LihatDataInstrukturActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                System.exit(1);

            }
        }

        UpdateDataInstruktur updateDataInstruktur = new UpdateDataInstruktur();
        updateDataInstruktur.execute();
    }

    private void confirmDeleteDataInstruktur() {
        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Are you sure want to delete this data?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataInstruktur() {
        class DeleteDataInstruktur extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(LihatDataInstrukturActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiInstruktur.URL_DELETE, id_ins);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(LihatDataInstrukturActivity.this,"Pesan: " + s,
                        Toast.LENGTH_SHORT).show();
                System.exit(1);
            }
        }
        DeleteDataInstruktur deleteDataInstruktur = new DeleteDataInstruktur();
        deleteDataInstruktur.execute();
    }
}