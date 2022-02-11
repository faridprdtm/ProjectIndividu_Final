package com.farid.projectindividu.ui.materi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.farid.projectindividu.HttpHandler;

import com.farid.projectindividu.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MateriFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button btn_add_mtr;
    ListView list_view_mtr;
    private String JSON_STRING;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materi, container, false);
        btn_add_mtr= view.findViewById(R.id.btn_add_mtr);
        btn_add_mtr.setOnClickListener(this);

        list_view_mtr=view.findViewById(R.id.list_view_mtr);
        list_view_mtr.setOnItemClickListener(this);

        getJSON();
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
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_GET_ALL_MTR);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA JSON: ", JSON_STRING);

                //menampilkan data dalam bentuk list view
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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiMateri.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_mat = object.getString(KonfigurasiMateri.TAG_JSON_ID);
                String nama_mat = object.getString(KonfigurasiMateri.TAG_JSON_NAMA);
                HashMap<String, String> peserta = new HashMap<>();
                peserta.put(KonfigurasiMateri.TAG_JSON_ID, id_mat);
                peserta.put(KonfigurasiMateri.TAG_JSON_NAMA, nama_mat);

                //ubah format JSON menjadi Array List
                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_materi,
                new String[]{KonfigurasiMateri.TAG_JSON_ID, KonfigurasiMateri.TAG_JSON_NAMA},
                new int[]{R.id.txt_id_materi, R.id.txt_nama_materi}
        );
        list_view_mtr.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), TambahMateriActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), LihatDetailMateriActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String mat_id = map.get(KonfigurasiMateri.TAG_JSON_ID).toString();
        intent.putExtra(KonfigurasiMateri.MAT_ID, mat_id);
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}