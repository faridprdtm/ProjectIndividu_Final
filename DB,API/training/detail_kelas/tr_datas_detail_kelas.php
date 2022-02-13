<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT a.id_detail_kls,c.nama_mat,d.nama_pst FROM tb_detail_kelas a 
	JOIN tb_kelas b ON a.id_kls = b.id_kls 
	JOIN tb_materi c ON b.id_mat = c.id_mat 
	JOIN tb_peserta d ON a.id_pst = d.id_pst";
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_detail_kls"=>$row['id_detail_kls'],
			"nama_mat"=>$row['nama_mat'],
            "nama_pst"=>$row['nama_pst']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>