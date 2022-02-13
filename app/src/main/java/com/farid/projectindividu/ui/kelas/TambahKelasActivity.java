package com.farid.projectindividu.ui.kelas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.R;
import com.farid.projectindividu.ui.instruktur.KonfigurasiInstruktur;
import com.farid.projectindividu.ui.materi.KonfigurasiMateri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TambahKelasActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText add_tgl_mulai, add_tgl_akhir;
    private Button btn_add_kls, btn_cancel_kls,btn_datepick_tgl_mulai,btn_datepick_tgl_akhir;
    private DatePickerDialog datePickerDialog;
    private Spinner spinner_materi_kelas,spinner_ins_kelas;
    private String spinner_value_ins, spinner_value_mat;
    private String JSON_STRING_INS, JSON_STRING_MAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);
        getSupportActionBar().setTitle("Tambah Kelas");
        add_tgl_mulai = findViewById(R.id.add_tgl_mulai);
        add_tgl_akhir = findViewById(R.id.add_tgl_akhir);

        btn_add_kls = findViewById(R.id.btn_add_kls);
        btn_cancel_kls = findViewById(R.id.btn_cancel_kls);
        btn_datepick_tgl_mulai = findViewById(R.id.btn_datepicker_tgl_mulai);
        btn_datepick_tgl_akhir = findViewById(R.id.btn_datepicker_tgl_akhir);
        spinner_ins_kelas = findViewById(R.id.spinner_ins_kelas);
        spinner_materi_kelas = findViewById(R.id.spinner_materi_kelas);

        btn_datepick_tgl_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalMulai();
            }
        });

        btn_datepick_tgl_akhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogeTanggalAkhir();
            }
        });
        btn_cancel_kls.setOnClickListener(this);
        btn_add_kls.setOnClickListener(this);

        getDataInstruktur();
        getDataMateri();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getDataMateri() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahKelasActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_GET_ALL_MTR);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_MAT = message;
                Log.d("DATA JSON: ", JSON_STRING_MAT);

                spinnerMateri();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerMateri() {
        JSONObject jsonObject = null;
        ArrayList<String> listId = new ArrayList<>();
        ArrayList<String> listNama = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_MAT);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiMateri.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_MAT);
            //Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_mat = object.getString(KonfigurasiMateri.TAG_JSON_ID);
                String nama_mat = object.getString(KonfigurasiMateri.TAG_JSON_NAMA);
                listId.add(id_mat);
                listNama.add(nama_mat);
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,listNama); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_materi_kelas.setAdapter(spinnerArrayAdapter);

            spinner_materi_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_mat = listId.get(i);
//                    Toast.makeText(KelasAddActivity.this, "True Value: "+spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getDataInstruktur() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahKelasActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiInstruktur.URL_GET_ALL);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_INS = message;
                Log.d("DATA JSON: ", JSON_STRING_INS);

                spinnerInstruktur();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerInstruktur() {
        JSONObject jsonObject = null;
        ArrayList<String> listIdIns = new ArrayList<>();
        ArrayList<String> listNamaIns = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_INS);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiMateri.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_INS);
            Toast.makeText(TambahKelasActivity.this, "DATA JSON Result: " + result, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_ID);
                String nama_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_NAMA);
                listIdIns.add(id_ins);
                listNamaIns.add(nama_ins);
            }
//            Toast.makeText(this, "test: "+listNamaKls.toString(), Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,listNamaIns); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_ins_kelas.setAdapter(spinnerArrayAdapter);

            spinner_ins_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_ins = listIdIns.get(i);
//                    Toast.makeText(KelasAddActivity.this, "True Value: "+spinner_value_ins, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void showDateDialogeTanggalAkhir() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                add_tgl_akhir.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    private void showDateDialogeTanggalMulai() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //txt_date.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()).toString());
                add_tgl_mulai.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_cancel_kls) {
            System.exit(1);
        } else if (v == btn_add_kls) {
            confirmAddDataKelas();
        }
    }

    private void confirmAddDataKelas() {
        //get value text field
        final String kom_tgl_mulai = add_tgl_mulai.getText().toString().trim();
        final String kom_tgl_akhir = add_tgl_akhir.getText().toString().trim();
//        final String kom_id_ins_kls = add_id_ins_kls.getText().toString().trim();
//        final String kom_id_mat_kls = add_id_mat_kls.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? \n" +
                "\n Tanggal Mulai : " + kom_tgl_mulai +
                "\n Tanggal Akhir : " + kom_tgl_akhir +
                "\n Instruktur : " + spinner_ins_kelas.getSelectedItem() +
                "\n Materi     : " + spinner_materi_kelas.getSelectedItem());
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataKelas() {
        final String tgl_mulai = add_tgl_mulai.getText().toString().trim();
        final String tgl_akhir = add_tgl_akhir.getText().toString().trim();
        final String id_ins_kls = spinner_value_ins;
        final String id_mat_kls = spinner_value_mat;

        class SimpanDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahKelasActivity.this,
                        "Menyimpan Data", "Harap tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KonfigurasiKelas.KEY_KLS_TGL_MULAI, tgl_mulai);
                params.put(KonfigurasiKelas.KEY_KLS_TGL_AKHIR, tgl_akhir);
                params.put(KonfigurasiKelas.KEY_KLS_ID_INS, id_ins_kls);
                params.put(KonfigurasiKelas.KEY_KLS_ID_MAT, id_mat_kls);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiKelas.URL_ADD, params);
                //System.out.println("Result" + params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahKelasActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                System.exit(1);
            }
        }
        SimpanDataKelas simpanDataKelas = new SimpanDataKelas();
        simpanDataKelas.execute();
    }

}