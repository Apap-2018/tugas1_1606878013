package com.apap.tugas1_1606878013.service;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.ProvinsiModel;

import java.util.List;

public interface InstansiService{
    List<InstansiModel> getInstansiByProvinsi (ProvinsiModel provinsiModel);
}
