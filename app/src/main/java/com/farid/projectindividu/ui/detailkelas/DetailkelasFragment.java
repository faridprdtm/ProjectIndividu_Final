package com.farid.projectindividu.ui.detailkelas;

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
import com.farid.projectindividu.ui.kelas.KonfigurasiKelas;
import com.farid.projectindividu.ui.kelas.LihatDetailKelasActivity;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DetailkelasFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
ListView list_view_detail_kelas;
Button btn_add_detail_kelas;
private String JSON_STRING;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_kelas, container, false);
        list_view_detail_kelas = view.findViewById(R.id.list_view_detail_kelas);
        btn_add_detail_kelas= view.findViewById(R.id.btn_add_detail_kelas);
        getJSON();
        list_view_detail_kelas.setOnItemClickListener(this);
        btn_add_detail_kelas.setOnClickListener(this);

        return view ;
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
                String result = handler.sendGetResponse(KonfigurasiDetailKelas.URL_GET_ALL);
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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiDetailKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_dtl_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID);
                String id_kls_dtl_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
                String id_pst_dtl_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);
                HashMap<String, String> detail = new HashMap<>();
                detail.put(KonfigurasiDetailKelas.TAG_JSON_ID, id_dtl_kls);
                detail.put(KonfigurasiDetailKelas.TAG_JSON_ID_KLS, id_kls_dtl_kls);
                detail.put(KonfigurasiDetailKelas.TAG_JSON_ID_PST, id_pst_dtl_kls);

                //ubah format JSON menjadi Array List
                list.add(detail);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_detail_kelas,
                new String[]{KonfigurasiDetailKelas.TAG_JSON_ID, KonfigurasiDetailKelas.TAG_JSON_ID_KLS,
                        KonfigurasiDetailKelas.TAG_JSON_ID_PST},
                new int[]{R.id.txt_id_detail, R.id.txt_nama_kls_detail, R.id.txt_nama_peserta_detail}
        );
        list_view_detail_kelas.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(),
                LihatDataDetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String dtlKlsId = map.get(KonfigurasiDetailKelas.TAG_JSON_ID).toString();
        myIntent.putExtra(KonfigurasiDetailKelas.DTL_KLS_ID, dtlKlsId);
        startActivity(myIntent);

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), TambahDataDetailKelasActivity.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}