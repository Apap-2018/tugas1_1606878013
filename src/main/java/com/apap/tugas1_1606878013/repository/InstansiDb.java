package com.apap.tugas1_1606878013.repository;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel, Long> {
    InstansiModel findById(long id);
    List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);
}