package com.apap.tugas1_1606878013.service;

import com.apap.tugas1_1606878013.model.JabatanModel;
import com.apap.tugas1_1606878013.repository.JabatanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
    @Autowired
    private JabatanDb jabatanDb;

    @Override
    public void addJabatan (JabatanModel jabatan) {
        jabatanDb.save(jabatan);
    }

    @Override
    public JabatanModel findJabatanById(long id){
        return jabatanDb.getOne(id);
    }

    @Override
    public void changeJabatan (JabatanModel beforeChangedModel, String nama, String deskripsi, Double gajiPokok) {
        beforeChangedModel.setNama(nama);
        beforeChangedModel.setDeskripsi(deskripsi);
        beforeChangedModel.setGajiPokok(gajiPokok);
        jabatanDb.save(beforeChangedModel);
    }
}
