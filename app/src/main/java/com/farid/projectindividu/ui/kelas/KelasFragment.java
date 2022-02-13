package com.farid.projectindividu.ui.kelas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.farid.projectindividu.HttpHandler;
import com.farid.projectindividu.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class KelasFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView list_view_kelas;
    Button btn_add_kelas;
    private String JSON_STRING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelas, container, false);
        list_view_kelas = (ListView) view.findViewById(R.id.list_view_kelas);
        btn_add_kelas = (Button) view.findViewById(R.id.btn_add_kelas);
        getJSON();
        list_view_kelas.setOnItemClickListener(this);
        btn_add_kelas.setOnClickListener(this);
        return view;
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),
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
                JSON_STRING = message;
                Log.d("DATA JSON: ", JSON_STRING);

                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID);
                String instruktur = object.getString(KonfigurasiKelas.TAG_JSON_ID_INS);
                String materi = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(KonfigurasiKelas.TAG_JSON_ID, id_kls);
                kelas.put(KonfigurasiKelas.TAG_JSON_ID_INS, instruktur);
                kelas.put(KonfigurasiKelas.TAG_JSON_ID_MAT, materi);

                //ubah format JSON menjadi Array List
                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_kelas,
                new String[]{KonfigurasiKelas.TAG_JSON_ID, KonfigurasiKelas.TAG_JSON_ID_INS,
                        KonfigurasiKelas.TAG_JSON_ID_MAT},
                new int[]{R.id.txt_id_kelas, R.id.txt_nama_ins_kls, R.id.txt_nama_mtr_kls}
        );
        list_view_kelas.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(),
                LihatDetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String insId = map.get(KonfigurasiKelas.TAG_JSON_ID).toString();
        myIntent.putExtra(KonfigurasiKelas.KLS_ID, insId);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), TambahKelasActivity.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}