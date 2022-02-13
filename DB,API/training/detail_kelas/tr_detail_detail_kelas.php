<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id_detail_kls = $_GET['id_detail_kls'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT a.id_detail_kls,c.nama_mat,d.nama_pst FROM tb_detail_kelas a 
	JOIN tb_kelas b ON a.id_kls = b.id_kls 
	JOIN tb_materi c ON b.id_mat = c.id_mat 
	JOIN tb_peserta d ON a.id_pst = d.id_pst
	WHERE id_detail_kls=$id_detail_kls";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"id_detail_kls"=>$row['id_detail_kls'],
		"nama_mat"=>$row['nama_mat'],
		"nama_pst"=>$row['nama_pst']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>