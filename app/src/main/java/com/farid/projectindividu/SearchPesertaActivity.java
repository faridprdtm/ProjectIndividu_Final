package com.farid.projectindividu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.farid.projectindividu.ui.peserta.Konfigurasi;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchPesertaActivity extends AppCompatActivity {
    Button btn_search_pst;
    EditText search_id_pst;
    TextView txt_cari_id_pst, txt_cari_nama_pst, txt_cari_email_pst,
             txt_cari_hp_pst,txt_cari_ins_pst;
    String cari_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_peserta);
        getSupportActionBar().setTitle("Search Peserta");
        btn_search_pst = findViewById(R.id.btn_search_pst);
        search_id_pst = findViewById(R.id.search_id_pst);
        txt_cari_id_pst = findViewById(R.id.txt_cari_id_pst);
        txt_cari_nama_pst = findViewById(R.id.txt_cari_nama_pst);
        txt_cari_email_pst = findViewById(R.id.txt_cari_email_pst);
        txt_cari_hp_pst = findViewById(R.id.txt_cari_hp_pst);
        txt_cari_ins_pst = findViewById(R.id.txt_cari_ins_pst);

        btn_search_pst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cariDataPeserta();
            }
        });

    }

    private void cariDataPeserta() {
        cari_value= search_id_pst.getText().toString();
        if (cari_value.equals("")){
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
                loading = ProgressDialog.show(SearchPesertaActivity.this,
                        "Mengambil Data", "Harap Menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL_PST, cari_value);
                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();

                if (message.contains("error")) {
//                    Toast.makeText(CariInstrukturActivity.this, "hasil: " + message, Toast.LENGTH_LONG).show();
                    alertMessage();
                    txt_cari_id_pst.setText("");
                    txt_cari_nama_pst.setText("");
                    txt_cari_email_pst.setText("");
                    txt_cari_hp_pst.setText("");
                    txt_cari_ins_pst.setText("");
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
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_cek_ins = object.getString(Konfigurasi.TAG_JSON_ID_PST);
            String nama_pst = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
            String email_pst = object.getString(Konfigurasi.TAG_JSON_EMAIL_PST);
            String hp_pst = object.getString(Konfigurasi.TAG_JSON_HP_PST);
            String ins_pst = object.getString(Konfigurasi.TAG_JSON_INSTANSI_PST);

            txt_cari_id_pst.setText("ID: " + cari_value);
            txt_cari_nama_pst.setText("Nama: " + nama_pst);
            txt_cari_email_pst.setText("Email: " + email_pst);
            txt_cari_hp_pst.setText("No Telp: " + hp_pst);
            txt_cari_ins_pst.setText("Instansi: " + ins_pst);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}