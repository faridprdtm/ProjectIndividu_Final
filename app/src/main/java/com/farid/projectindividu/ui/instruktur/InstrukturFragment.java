package com.farid.projectindividu.ui.instruktur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.farid.projectindividu.ui.materi.LihatDetailMateriActivity;
import com.farid.projectindividu.ui.materi.TambahMateriActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class InstrukturFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button btn_add_ins;
    ListView list_view_ins;
    private String JSON_STRING;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruktur, container, false);
        btn_add_ins= view.findViewById(R.id.btn_add_ins);
        btn_add_ins.setOnClickListener(this);

        list_view_ins=view.findViewById(R.id.list_view_ins);
        list_view_ins.setOnItemClickListener(this);

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
                String result = handler.sendGetResponse(KonfigurasiInstruktur.URL_GET_ALL);
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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiInstruktur.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_ID);
                String nama_ins = object.getString(KonfigurasiInstruktur.TAG_JSON_NAMA);
                HashMap<String, String> instruktur = new HashMap<>();
                instruktur.put(KonfigurasiInstruktur.TAG_JSON_ID, id_ins);
                instruktur.put(KonfigurasiInstruktur.TAG_JSON_NAMA, nama_ins);

                //ubah format JSON menjadi Array List
                list.add(instruktur);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_instruktur,
                new String[]{KonfigurasiInstruktur.TAG_JSON_ID, KonfigurasiInstruktur.TAG_JSON_NAMA},
                new int[]{R.id.txt_id_instruktur, R.id.txt_nama_instruktur}
        );
        list_view_ins.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), TambahInstrukturActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), LihatDataInstrukturActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String ins_id = map.get(KonfigurasiInstruktur.TAG_JSON_ID).toString();
        intent.putExtra(KonfigurasiInstruktur.INS_ID, ins_id);
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}