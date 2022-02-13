package com.farid.projectindividu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.farid.projectindividu.ui.instruktur.KonfigurasiInstruktur;
import com.farid.projectindividu.ui.peserta.Konfigurasi;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchInstrukturActivity extends AppCompatActivity {
    Button btn_search_ins;
    EditText search_id_ins;
    TextView txt_cari_id_ins, txt_cari_nama_ins, txt_cari_email_ins,
            txt_cari_hp_ins;
    String cari_value_ins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_instruktur);
        getSupportActionBar().setTitle("Search Instruktur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_search_ins = findViewById(R.id.btn_search_ins);
        search_id_ins = findViewById(R.id.search_id_ins);
        txt_cari_id_ins = findViewById(R.id.txt_cari_id_ins);
        txt_cari_nama_ins = findViewById(R.id.txt_cari_nama_ins);
        txt_cari_email_ins = findViewById(R.id.txt_cari_email_ins);
        txt_cari_hp_ins = findViewById(R.id.txt_cari_hp_ins);


        btn_search_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cariDataPeserta();
            }
        });

    }

    private void cariDataPeserta() {
        cari_value_ins= search_id_ins.getText().toString();
        if (cari_value_ins.equals("")){
            alertMessage();
        }else {
            getData();
        }
    }



    private void alertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Data Tidak Ditemukan");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
        builder.setCancelable(false);
        builder.setNegativeButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getData() {
        class GetData extends AsyncTask<Void, Void, String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(SearchInstrukturActivity.this,
                        "Mengambil Data", "Harap Menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiInstruktur.URL_GET_DETAIL, cari_value_ins);
                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();

                if (message.contains("error")) {
//                    Toast.makeText(CariInstrukturActivity.this, "hasil: " + message, Toast.LENGTH_LONG).show();
                    alertMessage();
                    txt_cari_id_ins.setText("");
                    txt_cari_nama_ins.setText("");
                    txt_cari_email_ins.setText("");
                    txt_cari_hp_ins.setText("");

                } else {
                    setDetailData(message);
                }
            }
        }
        GetData getDATA = new GetData();
        getDATA.execute();
    }

    private void setDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiInstruktur.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_cek_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_ID);
            String nama_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_NAMA);
            String email_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_EMAIL);
            String hp_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_HP);


            txt_cari_id_ins.setText("ID: " + cari_value_ins);
            txt_cari_nama_ins.setText("Nama: " + nama_ins);
            txt_cari_email_ins.setText("Email: " + email_ins);
            txt_cari_hp_ins.setText("No Telp: " + hp_ins);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}