package com.apap.tugas1_1606878013.controller;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.JabatanModel;
import com.apap.tugas1_1606878013.model.PegawaiModel;
import com.apap.tugas1_1606878013.model.ProvinsiModel;
import com.apap.tugas1_1606878013.repository.InstansiDb;
import com.apap.tugas1_1606878013.repository.JabatanDb;
import com.apap.tugas1_1606878013.repository.PegawaiDb;
import com.apap.tugas1_1606878013.repository.ProvinsiDb;
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
    private PegawaiDb pegawaiDb;

    @Autowired
    private JabatanDb jabatanDb;

    @Autowired
    private ProvinsiDb provinsiDb;

    @Autowired
    private InstansiDb instansiDb;

    @RequestMapping("/")
    private String home(Model model) {
        List<JabatanModel> jabatan = jabatanDb.findAll();
        List<InstansiModel> instansi = instansiDb.findAll();
        model.addAttribute("allJabatan", jabatan);
        model.addAttribute("allInstansi", instansi);
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
        List<ProvinsiModel> allProvinsi = provinsiDb.findAll();
        List<InstansiModel> allInstansi = instansiDb.findAll();
        List<JabatanModel> allJabatan = jabatanDb.findAll();
        model.addAttribute("newPegawai", new PegawaiModel());
        model.addAttribute("allProvinsi", allProvinsi);
        model.addAttribute("allInstansi",  allInstansi);
        model.addAttribute("allJabatan", allJabatan);
        return "add_pegawai";
    }

    /*@GetMapping(value = "/pegawai/tambah/submit")
    private String add(@RequestParam("nama") String nama,
                       @RequestParam("tempat_lahir") String tempatLahir,
                       @RequestParam("tanggal_lahir") Date tanggalLahir,
                       @RequestParam("tahun_masuk") String tahunMasuk,
                       @RequestParam("provinsi") long idProvinsi,
                       @RequestParam("instansi") long idInstansi,
                       @RequestParam("jabatan") long idJabatan)
    {
        ProvinsiModel provinsi = provinsiDb.getOne(idProvinsi);
        InstansiModel instansi = instansiDb.getOne(idInstansi);
        JabatanModel jabatan = jabatanDb.getOne(idJabatan);
        String tanggal = tanggalLahir.getDate() + "";
        if ((tanggal + "").length() == 1){
            tanggal =  ("0" + tanggal);
        }
        String bulan = tanggalLahir.getMonth() + 1 + "";
        if ((bulan + "").length() == 1){
            bulan = ("0" + bulan);
        }
        String tahun = (tanggalLahir.getYear() + "");
        String nip = instansi.getId() + "" + tanggal + bulan + tahun + tahunMasuk;

        List<PegawaiModel> pegawaiModels = pegawaiDb.findAll();
        int nomorUrut = 1;
        String tempNomorUrut = "";
        for (PegawaiModel pegawai : pegawaiModels){
            if (pegawai.getInstansi().getId() == instansi.getId() &&
                    pegawai.getInstansi().getProvinsi().getId() == provinsi.getId() &&
                    pegawai.getTahunMasuk() == tahunMasuk &&
                    pegawai.getTanggalLahir() == tanggalLahir){
                nomorUrut += 1;
            }
        }

        if ((nomorUrut + "").length() == 1){
            tempNomorUrut += "0" + nomorUrut;
        }

        nip += tempNomorUrut;
        System.out.println(nip);

        PegawaiModel newPegawai = new PegawaiModel();
        newPegawai.setNip(nip);
        newPegawai.setNama(nama);
        newPegawai.setTempatLahir(tempatLahir);
        newPegawai.setTanggalLahir(tanggalLahir);
        newPegawai.setTahunMasuk(tahunMasuk);

        InstansiModel instansiModel = instansiDb.getOne(idInstansi);
        newPegawai.setInstansi(instansiModel);

        ProvinsiModel provinsiModel = provinsiDb.getOne(idProvinsi);
        newPegawai.getInstansi().setProvinsi(provinsiModel);

        JabatanModel jabatanModel = jabatanDb.getOne(idJabatan);
        newPegawai.addJabatan(jabatanModel);

        pegawaiService.addPegawai(newPegawai);
        return "add_pegawai_response";
    }*/

    @GetMapping (value = "pegawai/termuda-tertua")
    private String seeOldestYoungestPegawai(@RequestParam("idInstansi") long idInstansi, Model model){
        InstansiModel instansiModel = instansiDb.getOne(idInstansi);
        List<PegawaiModel> daftarPegawai = instansiModel.getPegawai();
        String message = "";

        if (daftarPegawai.isEmpty()){
            message += "Tidak ada pegawai yang terdaftar dalam instansi ini";
            model.addAttribute("message", message);
            return "oldest_youngest_pegawai";
        }
        else if (daftarPegawai.size() == 1){
            message += "Hanya ada satu pegawai dalam instansi ini";
            model.addAttribute("massage", message);
            return "oldest_youngest_pegawai";
        }
        else {
            PegawaiModel pegawaiTermuda = daftarPegawai.get(0);
            PegawaiModel pegawaiTertua = daftarPegawai.get(0);
            for (int i = 0 ; i < daftarPegawai.size() ; i++){
                if (daftarPegawai.get(i).getUmur() < pegawaiTermuda.getUmur()){
                    pegawaiTermuda = daftarPegawai.get(i);
                }
                else if (daftarPegawai.get(i).getUmur() > pegawaiTertua.getUmur()){
                    pegawaiTertua = daftarPegawai.get(i);
                }
            }
            model.addAttribute("message", message);
            model.addAttribute("pegawaiTermuda", pegawaiTermuda);
            model.addAttribute("pegawaiTertua", pegawaiTertua);
            return "oldest_youngest_pegawai";
        }

    }
}
