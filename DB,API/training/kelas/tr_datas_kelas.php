<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT a.id_kls, a.tgl_mulai_kls, a.tgl_akhir_kls,c.nama_ins, b.nama_mat FROM tb_kelas a JOIN tb_materi b ON a.id_mat = b.id_mat JOIN tb_instruktur c ON a.id_ins = c.id_ins; ";
	
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_kls"=>$row['id_kls'],
			"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"tgl_akhir_kls"=>$row['tgl_akhir_kls'],
            "nama_ins"=>$row['nama_ins'],
            "nama_mat"=>$row['nama_mat']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>