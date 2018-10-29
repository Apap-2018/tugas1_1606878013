package com.apap.tugas1_1606878013.repository;

import com.apap.tugas1_1606878013.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Long> {
    Long findById(long id);
}
