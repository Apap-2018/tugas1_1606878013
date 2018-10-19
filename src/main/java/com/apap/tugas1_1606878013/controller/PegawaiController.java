package com.apap.tugas1_1606878013.controller;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.JabatanModel;
import com.apap.tugas1_1606878013.model.PegawaiModel;
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

    @RequestMapping("/")
    private String home() {
        return "home";
    }

    /*
        Fitur 1 : Menampilkan Data Pegawai Berdasarkan NIP
     */
    @GetMapping(value = "/pegawai")
    private String view(@RequestParam("nip") String nip, Model model) {
        PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
        InstansiModel instansiPegawai = pegawai.getInstansi();
        List<JabatanModel> jabatanPegawai = pegawai.getJabatanPegawai();
        Double gajiPokokTerbesar = pegawai.hitungGajiPokokTerbesar();
        model.addAttribute("pegawai", pegawai);
        model.addAttribute("instansiPegawai", instansiPegawai);
        model.addAttribute("jabatanPegawai", jabatanPegawai);
        model.addAttribute("gajiPokok", gajiPokokTerbesar);
        return "view_pegawai";
    }

    @GetMapping(value = "/pegawai/tambah")
    private String add(Model model) {
        model.addAttribute("new_pegawai", new PegawaiModel());
        return "add_pegawai";
    }

    @PostMapping(value = "/pegawai/tambah")
    private String add(@ModelAttribute PegawaiModel pegawai) {
        pegawaiService.addPegawai(pegawai);
        return "add_pegawai_response";
    }
}
