package com.farid.projectindividu.ui.instruktur;

public class KonfigurasiInstruktur {
    // url web API berada
    public static final String URL_GET_ALL = "http://192.168.1.6/training/instruktur/tr_datas_instruktur.php";
    public static final String URL_GET_DETAIL = "http://192.168.1.6/training/instruktur/tr_detail_instruktur.php?id_ins=";
    public static final String URL_ADD = "http://192.168.1.6/training/instruktur/tr_add_instruktur.php";
    public static final String URL_UPDATE = "http://192.168.1.6/training/instruktur/tr_update_instruktur.php";
    public static final String URL_DELETE = "http://192.168.1.6/training/instruktur/tr_delete_instruktur.php?id_ins=";

    // key and value json yang muncul di browser
    public static final String KEY_INS_ID = "id_ins";
    public static final String KEY_INS_NAMA = "nama_ins";
    public static final String KEY_INS_EMAIL = "email_ins";
    public static final String KEY_INS_HP = "hp_ins";

    // flwg json
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_ins";
    public static final String TAG_JSON_NAMA = "nama_ins";
    public static final String TAG_JSON_EMAIL = "email_ins";
    public static final String TAG_JSON_HP = "hp_ins";

    //variable ID Pegawai
    public static final String INS_ID = "ins_id";
}
