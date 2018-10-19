package com.apap.tugas1_1606878013.service;

import com.apap.tugas1_1606878013.model.PegawaiModel;

public interface PegawaiService {
    PegawaiModel getPegawaiByNip(String nip);
    void addPegawai(PegawaiModel pegawai);
}
