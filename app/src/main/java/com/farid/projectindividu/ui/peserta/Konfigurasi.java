package com.farid.projectindividu.ui.peserta;

public class Konfigurasi {
    // url dimana web API berada PESERTA
    public static final String URL_GET_ALL_PST = "http://192.168.1.4/training/peserta/tr_datas_peserta.php";
    public static final String URL_GET_DETAIL_PST = "http://192.168.1.4/training/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_ADD_PST = "http://192.168.1.4/training/peserta/tr_add_peserta.php";
    public static final String URL_UPDATE_PST = "http://192.168.1.4//training/peserta/tr_update_peserta.php";
    public static final String URL_DELETE_PST = "http://192.168.1.4/training/peserta/tr_delete_peserta.php?id_pst=";


    //key and value JSON yang muncul di browser PESERTA
    public static final String KEY_PST_ID="id_pst";
    public static final String KEY_PST_NAMA="nama_pst";
    public static final String KEY_PST_EMAIL="email_pst";
    public static final String KEY_PST_HP="hp_pst";
    public static final String KEY_PST_INSTANSI="instansi_pst";

    //flag JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_JSON_ID_PST="id_pst";
    public static final String TAG_JSON_NAMA_PST="nama_pst";
    public static final String TAG_JSON_EMAIL_PST="email_pst";
    public static final String TAG_JSON_HP_PST="hp_pst";
    public static final String TAG_JSON_INSTANSI_PST="instansi_pst";

    //variable ID pegawai
    public static final String PST_ID="pst_id";
}
