package com.apap.tugas1_1606878013.controller;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.JabatanModel;
import com.apap.tugas1_1606878013.model.PegawaiModel;
import com.apap.tugas1_1606878013.repository.JabatanDb;
import com.apap.tugas1_1606878013.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanDb jabatanDb;

    @RequestMapping("/")
    private String home(Model model) {
        List<JabatanModel> jabatan = jabatanDb.findAll();
        model.addAttribute("allJabatan", jabatan);
        return "home";
    }

    /*
        Fitur 1 : Menampilkan Data Pegawai Berdasarkan NIP
     */
    @GetMapping(value = "/pegawai")
    private String view(@RequestParam("nip") String nip, Model model) {
        PegawaiModel pegawai = pegawaiService.findPegawaiByNip(nip);
        InstansiModel instansiPegawai = pegawai.getInstansi();
        List<JabatanModel> jabatanPegawai = pegawai.getJabatanPegawai();
        Double gajiPokokTerbesar = pegawai.hitungGajiPokokTerbesar();
        model.addAttribute("pegawai", pegawai);
        model.addAttribute("instansiPegawai", instansiPegawai);
        model.addAttribute("jabatanPegawai", jabatanPegawai);
        model.addAttribute("gajiPokok", gajiPokokTerbesar);
        return "view_pegawai";
    }

    /*
        Fitur 2 : Menambahkan Data Pegawai di Suatu Instansi
     */
    @GetMapping(value = "/pegawai/tambah")
    private String add(Model model) {
        model.addAttribute("newPegawai", new PegawaiModel());
        return "add_pegawai";
    }

    @PostMapping(value = "/pegawai/tambah")
    private String add(@ModelAttribute PegawaiModel pegawai) {
        pegawaiService.addPegawai(pegawai);
        return "add_pegawai_response";
    }
}
