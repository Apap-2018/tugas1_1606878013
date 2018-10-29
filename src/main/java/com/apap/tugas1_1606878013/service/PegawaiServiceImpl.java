package com.apap.tugas1_1606878013.service;

import com.apap.tugas1_1606878013.model.PegawaiModel;
import com.apap.tugas1_1606878013.repository.PegawaiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
    @Autowired
    private PegawaiDb pegawaiDb;

    @Override
    public PegawaiModel findPegawaiByNip(String nip){
        return pegawaiDb.findByNip(nip);
    }
    @Override
    public void addPegawai(PegawaiModel pegawai) {
        pegawaiDb.save(pegawai);
    }
    @Override
    public void updatePegawai(PegawaiModel pegawai) { pegawaiDb.save(pegawai);}
}
