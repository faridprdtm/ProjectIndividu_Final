-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 13 Feb 2022 pada 22.05
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inixtraining`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_detail_kelas`
--

CREATE TABLE `tb_detail_kelas` (
  `id_detail_kls` int(10) NOT NULL,
  `id_kls` int(10) NOT NULL,
  `id_pst` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_detail_kelas`
--

INSERT INTO `tb_detail_kelas` (`id_detail_kls`, `id_kls`, `id_pst`) VALUES
(1, 6, 2),
(3, 2, 7),
(4, 6, 7),
(5, 1, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_instruktur`
--

CREATE TABLE `tb_instruktur` (
  `id_ins` int(10) NOT NULL,
  `nama_ins` varchar(50) NOT NULL,
  `email_ins` varchar(50) NOT NULL,
  `hp_ins` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_instruktur`
--

INSERT INTO `tb_instruktur` (`id_ins`, `nama_ins`, `email_ins`, `hp_ins`) VALUES
(1, 'Febri', 'febri@gmail.com', '081192001383'),
(2, 'Yusuf', 'yusuf@gmail.com', '081311221144'),
(3, 'Fuad', 'fuad@gmail.com', '089766445588'),
(4, 'Dewi', 'dewi@gmail.com', '085711232139'),
(5, 'Desi Damayanti', 'desi@gmail.com', '085716290871');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_kelas`
--

CREATE TABLE `tb_kelas` (
  `id_kls` int(10) NOT NULL,
  `tgl_mulai_kls` date NOT NULL,
  `tgl_akhir_kls` date NOT NULL,
  `id_ins` int(10) NOT NULL,
  `id_mat` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_kelas`
--

INSERT INTO `tb_kelas` (`id_kls`, `tgl_mulai_kls`, `tgl_akhir_kls`, `id_ins`, `id_mat`) VALUES
(1, '2022-02-01', '2022-05-01', 1, 1),
(2, '2022-01-09', '2022-03-09', 2, 2),
(3, '2022-01-12', '2022-01-19', 4, 2),
(4, '2022-01-02', '2022-01-26', 1, 1),
(5, '2022-01-02', '2022-01-25', 1, 1),
(6, '2022-01-01', '2022-03-01', 3, 5),
(7, '2022-02-01', '2022-04-01', 5, 4),
(8, '0000-00-00', '2022-03-01', 3, 3),
(9, '2022-02-01', '2022-04-01', 3, 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_materi`
--

CREATE TABLE `tb_materi` (
  `id_mat` int(10) NOT NULL,
  `nama_mat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_materi`
--

INSERT INTO `tb_materi` (`id_mat`, `nama_mat`) VALUES
(1, 'DevOps'),
(2, 'Linux Redhat'),
(3, 'UI/UX'),
(4, 'FigJam'),
(5, 'Comptia+');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_peserta`
--

CREATE TABLE `tb_peserta` (
  `id_pst` int(10) NOT NULL,
  `nama_pst` varchar(50) NOT NULL,
  `email_pst` varchar(50) NOT NULL,
  `hp_pst` varchar(13) NOT NULL,
  `instansi_pst` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_peserta`
--

INSERT INTO `tb_peserta` (`id_pst`, `nama_pst`, `email_pst`, `hp_pst`, `instansi_pst`) VALUES
(1, 'Farid Pridiatama', 'farid@gmail.com', '085722233447', 'Maybank'),
(2, 'Fadil Aprinda', 'fadil@gmail.com', '081322325467', 'BSI'),
(3, 'Putri Pertama', 'putri@gmail.com', '089711223123', 'Maybank'),
(4, 'Akmal', 'akmal@gmail.com', '081234908716', 'BSI'),
(5, 'Mawar', 'mawar@gmail.com', '089781293147', 'Maybank'),
(6, 'Syifa', 'syifa@gmail.com', '085711290192', 'BSI'),
(7, 'Bagas', 'bagas@gmail.com', '081345678209', 'Alfamart'),
(9, 'putra', 'putra@gmail.com', '089781212233', 'Bea Cukai'),
(10, 'Acha', 'acha@gmail.com', '085780903321', 'Maybank'),
(13, 'Agnes', 'agnes@gmail.com', '089710283877', 'BSI'),
(15, 'ajo', 'ajo@gmail.com', '089900223311', 'Maybank'),
(16, 'kusuma', 'kusuma@gmail.com', '089612313113', 'Alfamart');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD PRIMARY KEY (`id_detail_kls`),
  ADD KEY `id_kls` (`id_kls`),
  ADD KEY `id_pst` (`id_pst`);

--
-- Indeks untuk tabel `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  ADD PRIMARY KEY (`id_ins`);

--
-- Indeks untuk tabel `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD PRIMARY KEY (`id_kls`),
  ADD KEY `id_ins` (`id_ins`),
  ADD KEY `id_mat` (`id_mat`);

--
-- Indeks untuk tabel `tb_materi`
--
ALTER TABLE `tb_materi`
  ADD PRIMARY KEY (`id_mat`);

--
-- Indeks untuk tabel `tb_peserta`
--
ALTER TABLE `tb_peserta`
  ADD PRIMARY KEY (`id_pst`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  MODIFY `id_detail_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  MODIFY `id_ins` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `tb_kelas`
--
ALTER TABLE `tb_kelas`
  MODIFY `id_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `tb_materi`
--
ALTER TABLE `tb_materi`
  MODIFY `id_mat` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `tb_peserta`
--
ALTER TABLE `tb_peserta`
  MODIFY `id_pst` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD CONSTRAINT `tb_detail_kelas_ibfk_1` FOREIGN KEY (`id_kls`) REFERENCES `tb_kelas` (`id_kls`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_detail_kelas_ibfk_2` FOREIGN KEY (`id_pst`) REFERENCES `tb_peserta` (`id_pst`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD CONSTRAINT `tb_kelas_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `tb_instruktur` (`id_ins`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_kelas_ibfk_2` FOREIGN KEY (`id_mat`) REFERENCES `tb_materi` (`id_mat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
