package com.apap.tugas1_1606878013.service;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.ProvinsiModel;
import com.apap.tugas1_1606878013.repository.InstansiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
    @Autowired
    InstansiDb instansiDb;

    @Override
    public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsiModel){
        return instansiDb.findByProvinsi(provinsiModel);
    }
}
