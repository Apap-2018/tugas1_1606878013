package com.apap.tugas1_1606878013.repository;

import com.apap.tugas1_1606878013.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {

}
