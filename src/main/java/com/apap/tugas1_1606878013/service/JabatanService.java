package com.apap.tugas1_1606878013.service;

import com.apap.tugas1_1606878013.model.JabatanModel;

public interface JabatanService {
    void addJabatan(JabatanModel jabatan);
    JabatanModel findJabatanById(long id);
    void changeJabatan(JabatanModel beforeChangedModel, String nama, String deskripsi, Double gajiPokok);
}
