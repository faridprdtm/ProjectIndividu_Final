package com.farid.projectindividu.ui.detailkelas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TambahDataDetailKelasActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_add_dtl, btn_lihat_dtl;
    Spinner dropdown_kelas,dropdown_pst;
    private int spinner_value, spinner_value_kelas;
    private String JSON_STRING, JSON_STRING_KLS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_detail_kelas);
        getSupportActionBar().setTitle("Tambah Data");
        btn_add_dtl = findViewById(R.id.btn_add_dtl);
        btn_lihat_dtl = findViewById(R.id.btn_lihat_dtl);
        dropdown_kelas = findViewById(R.id.dropdown_kelas);
        dropdown_pst = findViewById(R.id.dropdown_pst);

        btn_add_dtl.setOnClickListener(this);
        btn_lihat_dtl.setOnClickListener(this);

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
                loading = ProgressDialog.show(TambahDataDetailKelasActivity.this,
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
//            Toast.makeText(TambahDataDetailKelasActivity.this, "DATA JSON Result: " + result, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID);
                String nama_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);
                listIdKls.add(id_kls);
                listNamaKls.add(nama_kls);
            }
//            Toast.makeText(this, "test: "+listNamaKls.toString(), Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,listNamaKls); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown_kelas.setAdapter(spinnerArrayAdapter);

            dropdown_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_kelas = Integer.parseInt(listIdKls.get(i));
                    Toast.makeText(TambahDataDetailKelasActivity.this, "True Value: "+spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

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
                loading = ProgressDialog.show(TambahDataDetailKelasActivity.this,
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
                    (this, android.R.layout.simple_spinner_item,listNama); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown_pst.setAdapter(spinnerArrayAdapter);

            dropdown_pst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value = Integer.parseInt(listId.get(i));
                    Toast.makeText(TambahDataDetailKelasActivity.this, "True Value: "+spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_lihat_dtl) {
            System.exit(1);
        } else if (v == btn_add_dtl) {
            confirmAddDataDetailKelas();
        }
    }
    private void confirmAddDataDetailKelas() {
        //get value text field
//        final String kom_id_kls = add_id_kls_dtl_kls.getText().toString().trim();
//        final String kom_id_pst = add_id_pst_dtl_kls.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Kelas    : " + dropdown_kelas.getSelectedItem() +
                "\n Peserta : " + dropdown_pst.getSelectedItem());
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataDetailKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataDetailKelas() {
//        final String id_kls = add_id_kls_dtl_kls.getText().toString().trim();
//        final String id_pst = add_id_pst_dtl_kls.getText().toString().trim();
        final String id_kls_spinner = String.valueOf(spinner_value_kelas);
        final String id_pst_spinner = String.valueOf(spinner_value);

        class SimpanDataDetailKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahDataDetailKelasActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_KLS, id_kls_spinner);
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_PST, id_pst_spinner);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiDetailKelas.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahDataDetailKelasActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                System.exit(1);

            }
        }
        SimpanDataDetailKelas simpanDataDetailKelas = new SimpanDataDetailKelas();
        simpanDataDetailKelas.execute();
    }

}