package com.apap.tugas1_1606878013.controller;

import com.apap.tugas1_1606878013.model.JabatanModel;
import com.apap.tugas1_1606878013.repository.JabatanDb;
import com.apap.tugas1_1606878013.service.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class JabatanController {
    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private JabatanDb jabatanDb;

    /*
    Fitur 5 - Menambahkan Jabatan Baru
     */
    @GetMapping(value = "/jabatan/tambah")
    private String add(Model model) {
        model.addAttribute("newJabatan", new JabatanModel());
        return "add_jabatan";
    }

    @PostMapping(value = "/jabatan/tambah")
    private String add(@ModelAttribute JabatanModel jabatan) {
        jabatanService.addJabatan(jabatan);
        return "add_jabatan_response";
    }

    /*
    Fitur 6 - Menampilkan Jabatan
     */
    @GetMapping(value = "/jabatan/view")
    private String view (@RequestParam("idJabatan") long idJabatan, Model model) {
        JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
        int jumlahPegawai = jabatan.getPegawai().size();
        model.addAttribute("jabatan", jabatan);
        model.addAttribute("jumlahPegawai", jumlahPegawai);

        DecimalFormat df = new DecimalFormat("#,###");
        model.addAttribute("gaji", df.format(jabatan.getGajiPokok()));
        return "view_jabatan";
    }

    /*
    Fitur 7 - Mengubah Data Jabatan
     */
    @GetMapping(value = "/jabatan/ubah")
    private String update (@RequestParam("idJabatan") long idJabatan, Model model){
        JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
        model.addAttribute("jabatan", jabatan);
        return "change_jabatan";
    }

    @PostMapping(value = "/jabatan/ubah")
    private String change(@RequestParam ("idJabatan") long id,
                          @RequestParam ("nama") String nama,
                          @RequestParam ("deskripsi") String deskripsi,
                          @RequestParam ("gajiPokok") Double gajiPokok){
        JabatanModel beforeChangedJabatanModel = jabatanService.findJabatanById(id);
        jabatanService.changeJabatan(beforeChangedJabatanModel, nama, deskripsi, gajiPokok);
        return "change_jabatan_response";
    }

    /*
    Fitur 8 - Menghapus Data Jabatan
     */
    @PostMapping(value = "/jabatan/hapus/{idJabatan}")
    private String delete (@PathVariable(value = "idJabatan") long id) {
        JabatanModel jabatan = jabatanService.findJabatanById(id);
        System.out.println(jabatan.getId());
        System.out.println(jabatan.getNama());
        jabatanService.deleteJabatan(jabatan);
        return "delete_jabatan_response";
    }

    /*
    Fitur 9 - Menampilkan Data Jabatan
     */
    @GetMapping(value = "/jabatan/viewall")
    private String viewAll(Model model) {
        List<JabatanModel> allJabatan = jabatanDb.findAll();
        model.addAttribute("allJabatan", allJabatan);
        return "view_all_jabatan";
    }
}
