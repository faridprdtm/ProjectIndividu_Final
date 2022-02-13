<?php 
 //Mendapatkan Nilai ID
 $id_detail_kls = $_GET['tb_kelas'];
 
 //Import File Koneksi Database
 require_once('../koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM tb_kelas WHERE id_kls=$id_kls;";

 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Kelas';
 }else{
 echo 'Gagal Menghapus Kelas';
 }
 
 mysqli_close($con);
 ?>