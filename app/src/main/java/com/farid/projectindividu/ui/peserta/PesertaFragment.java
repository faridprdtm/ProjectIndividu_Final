package com.farid.projectindividu.ui.peserta;


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

public class PesertaFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private String JSON_STRING;
    ListView list_view_pst;
    Button btn_add_pst_frag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_peserta, container, false);

        list_view_pst = view.findViewById(R.id.list_view_mtr);
        list_view_pst.setOnItemClickListener(this);

        btn_add_pst_frag = view.findViewById(R.id.btn_add_mtr);
        btn_add_pst_frag.setOnClickListener(this);

        getJSON();

        return view;
    }


    private void getJSON() {

        //bantuan dari class AsyncTask
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
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PST);
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
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_pst = object.getString(Konfigurasi.TAG_JSON_ID_PST);
                String nama_pst = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
                HashMap<String, String> peserta = new HashMap<>();
                peserta.put(Konfigurasi.TAG_JSON_ID_PST, id_pst);
                peserta.put(Konfigurasi.TAG_JSON_NAMA_PST, nama_pst);

                //ubah format JSON menjadi Array List
                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_layout,
                new String[]{Konfigurasi.TAG_JSON_ID_PST, Konfigurasi.TAG_JSON_NAMA_PST},
                new int[]{R.id.txt_id_peserta, R.id.txt_nama_peserta}
        );
        list_view_pst.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(), LihatDetailPesertaActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String pstId = map.get(Konfigurasi.TAG_JSON_ID_PST).toString();
        intent.putExtra(Konfigurasi.PST_ID, pstId);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(),TambahPesertaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}