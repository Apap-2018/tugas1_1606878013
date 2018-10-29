package com.apap.tugas1_1606878013.repository;

import com.apap.tugas1_1606878013.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByNip (String nip);
    List<PegawaiModel> findByTahunMasukAndTanggalLahir (String tahunMasuk, Date tanggalLahir);
}
