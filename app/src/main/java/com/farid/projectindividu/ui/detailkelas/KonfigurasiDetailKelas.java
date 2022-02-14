package com.farid.projectindividu.ui.detailkelas;

public class KonfigurasiDetailKelas {
    // url web API berada
    public static final String URL_GET_ALL = "http://192.168.1.6/training/detail_kelas/tr_datas_detail_kelas.php";
    public static final String URL_GET_DETAIL = "http://192.168.1.6/training/detail_kelas/tr_detail_detail_kelas.php?id_detail_kls=";
    public static final String URL_ADD = "http://192.168.1.6/training/detail_kelas/tr_add_detail_kelas.php";
    public static final String URL_UPDATE = "http://192.168.1.6/training/detail_kelas/tr_update_detail_kelas.php";
    public static final String URL_DELETE = "http://192.168.1.6/training/detail_kelas/tr_delete_detail_kelas.php?id_detail_kls=";

    // key and value json yang muncul di browser
    public static final String KEY_DTL_KLS_ID = "id_detail_kls";
    public static final String KEY_DTL_KLS_ID_KLS = "id_kls";
    public static final String KEY_DTL_KLS_ID_PST = "id_pst";

    // flag json
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_detail_kls";
    public static final String TAG_JSON_ID_KLS = "nama_mat";
    public static final String TAG_JSON_ID_PST = "nama_pst";

    //variable ID Pegawai
    public static final String DTL_KLS_ID = "dtl_kls_id";
}
