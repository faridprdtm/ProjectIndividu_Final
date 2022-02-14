package com.farid.projectindividu.ui.detailkelas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.R;
import com.farid.projectindividu.ui.kelas.KonfigurasiKelas;
import com.farid.projectindividu.ui.peserta.Konfigurasi;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatDataDetailKelasActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText edit_id_dtl_kls,edit_id_kls_mtr,edit_id_pst_kls;
    String id_dtl_kls, public_nama, public_kelas;
    Button btn_update_detail_kls,btn_delete_detail_kls;
    Spinner spinner_nama_kls,spinner_nama_pst;
    private int spinner_value, spinner_value_kelas;
    private String JSON_STRING, JSON_STRING_KLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_detail_kelas);

        getSupportActionBar().setTitle("Detail Kelas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_id_dtl_kls = findViewById(R.id.edit_id_dtl_kls);


        btn_update_detail_kls = findViewById(R.id.btn_update_detail_kls);
        btn_delete_detail_kls = findViewById(R.id.btn_delete_detail_kls);
        spinner_nama_kls = findViewById(R.id.spinner_nama_kls);
        spinner_nama_pst = findViewById(R.id.spinner_nama_pst);

        Intent receiveIntent = getIntent();
        id_dtl_kls = receiveIntent.getStringExtra(KonfigurasiDetailKelas.DTL_KLS_ID);
        edit_id_dtl_kls.setText(id_dtl_kls);

        getJSON();

        btn_update_detail_kls.setOnClickListener(this);
        btn_delete_detail_kls.setOnClickListener(this);

        getDataKelas();
        getDataPesetra();
    
    }
    private void getDataKelas() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataDetailKelasActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_GET_ALL);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_KLS = message;
                Log.d("DATA JSON: ", JSON_STRING_KLS);

                spinnerKelas();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void spinnerKelas() {

        JSONObject jsonObject = null;
        ArrayList<String> listIdKls = new ArrayList<>();
        ArrayList<String> listNamaKls = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_KLS);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_KLS);
//            Toast.makeText(DetailKelasDetailActivity.this, "DATA JSON Result: " + result, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID);
                String nama_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);
                listIdKls.add(id_kls);
                listNamaKls.add(nama_kls);
            }
//            Toast.makeText(this, "test: "+listNamaKls.toString(), Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, listNamaKls); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_nama_kls.setAdapter(spinnerArrayAdapter);

            spinner_nama_kls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_kelas = Integer.parseInt(listIdKls.get(i));
//                    Toast.makeText(DetailKelasDetailActivity.this, "True Value: "+spinner_value_kelas, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinner_nama_kls.setSelection(listNamaKls.indexOf(public_kelas));//set selected value in spinner

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getDataPesetra() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataDetailKelasActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PST);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA JSON: ", JSON_STRING);

                spinnerPeserta();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void spinnerPeserta() {
        JSONObject jsonObject = null;
        ArrayList<String> listId = new ArrayList<>();
        ArrayList<String> listNama = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
            //Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_pst = object.getString(Konfigurasi.TAG_JSON_ID_PST);
                String nama_pst = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
                listId.add(id_pst);
                listNama.add(nama_pst);
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, listNama); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_nama_pst.setAdapter(spinnerArrayAdapter);

            spinner_nama_pst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value = Integer.parseInt(listId.get(i));
//                    Toast.makeText(LihatDataDetailKelasActivity.this, "True Value: " + spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinner_nama_pst.setSelection(listNama.indexOf(public_nama));//set selected value in spinner

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void, Void, String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataDetailKelasActivity.this,
                        "Mengambil Data", "Harap Menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiDetailKelas.URL_GET_DETAIL, id_dtl_kls);
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
    private void displayDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiDetailKelas.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

//            String id_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
//            String id_pst = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);
            String id_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
            String id_pst = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);

            public_kelas = id_kls;
            public_nama = id_pst;

//            edit_id_kls_dtl_kls.setText(id_kls);
//            edit_id_pst_dtl_kls.setText(id_pst);
        } catch (Exception ex) {
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
        if (myButton == btn_update_detail_kls) {
            updateDataDetailKelas();
        } else if (myButton == btn_delete_detail_kls) {
            confirmDeleteDataDetailKelas();
        }
    }
    private void updateDataDetailKelas() {
        // variable data pegawai yang akan diubah
        final String id_kelas = Integer.toString(spinner_value_kelas);
        final String id_peserta = Integer.toString(spinner_value);
        Toast.makeText(LihatDataDetailKelasActivity.this, "id peserta: " + id_peserta, Toast.LENGTH_SHORT).show();

        class UpdateDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataDetailKelasActivity.this,
                        "Mengubah Data", "Harap Tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID, id_dtl_kls);
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_KLS, id_kelas);
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_PST,id_peserta);

//                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_PST, "3");
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiDetailKelas.URL_UPDATE, params);


                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Fragment fragment = null;

                Toast.makeText(LihatDataDetailKelasActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                System.exit(1);

            }
        }

        UpdateDataDetailKelas updateDataDetailKelas = new UpdateDataDetailKelas();
        updateDataDetailKelas.execute();
    }
    private void confirmDeleteDataDetailKelas() {
        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Are you sure want to delete this data?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataDetailKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataDetailKelas() {
        class DeleteDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(LihatDataDetailKelasActivity.this,
                        "Menghapus Data", "Harap Tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiDetailKelas.URL_DELETE, id_dtl_kls);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(LihatDataDetailKelasActivity.this, "Pesan: " + s,
                        Toast.LENGTH_SHORT).show();
                System.exit(1);
            }
        }
        DeleteDataDetailKelas deleteDataDetailKelas = new DeleteDataDetailKelas();
        deleteDataDetailKelas.execute();
    }

}