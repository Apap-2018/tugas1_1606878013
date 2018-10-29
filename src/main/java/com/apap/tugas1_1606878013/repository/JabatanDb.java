package com.apap.tugas1_1606878013.repository;

import com.apap.tugas1_1606878013.model.JabatanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel, Long> {
    Long findById(long id);
}
