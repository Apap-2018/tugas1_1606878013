package com.apap.tugas1_1606878013.controller;

import com.apap.tugas1_1606878013.model.InstansiModel;
import com.apap.tugas1_1606878013.model.JabatanModel;
import com.apap.tugas1_1606878013.model.PegawaiModel;
import com.apap.tugas1_1606878013.model.ProvinsiModel;
import com.apap.tugas1_1606878013.repository.InstansiDb;
import com.apap.tugas1_1606878013.repository.JabatanDb;
import com.apap.tugas1_1606878013.repository.PegawaiDb;
import com.apap.tugas1_1606878013.repository.ProvinsiDb;
import com.apap.tugas1_1606878013.service.InstansiService;
import com.apap.tugas1_1606878013.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private InstansiService instansiService;

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

        PegawaiModel pegawai = new PegawaiModel();
        pegawai.setJabatanPegawai(new ArrayList<JabatanModel>());
        pegawai.getJabatanPegawai().add(new JabatanModel());

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("allProvinsi", allProvinsi);
        model.addAttribute("allInstansi",  allInstansi);
        model.addAttribute("allJabatan", allJabatan);
        return "add_pegawai";
    }

/*    @GetMapping(value = "/pegawai/instansi")
    @ResponseBody
    public List<InstansiModel> getInstansiByProvinsiId (@RequestParam(value = "provinsiId", required = true) long id){
        ProvinsiModel provinsiModel = provinsiDb.getOne(id);
        return instansiService.getInstansiByProvinsi(provinsiModel);
    }*/

    @PostMapping(value = "/pegawai/tambah")
    private String add(@ModelAttribute PegawaiModel pegawai, Model model){

        int tempTanggal = pegawai.getTanggalLahir().getDate();
        String tanggal = "";
        if ((tempTanggal + "").length() == 1){
            tanggal = "0" + tempTanggal;
        }

        int bulan = pegawai.getTanggalLahir().getMonth() + 1;
        int tahun = pegawai.getTanggalLahir().getYear();
//        String tahun = (tempTahun + "").substring(2,4);

        long kodeInstansi = pegawai.getInstansi().getId();
        String tahunMasuk = pegawai.getTahunMasuk();
        List<PegawaiModel> seluruhPegawai = pegawaiDb.findByTahunMasukAndTanggalLahir(pegawai.getTahunMasuk(), pegawai.getTanggalLahir());


        for (int i  =  0 ;  i < seluruhPegawai.size() ; i++){
            System.out.println(seluruhPegawai.get(i).getNip());
        }


        Collections.sort(seluruhPegawai, new Comparator<PegawaiModel>() {
            @Override
            public int compare(PegawaiModel o1, PegawaiModel o2) {
                long p1 = o1.getId();
                long p2 = o2.getId();

                if (p1 > p2) {
                    return 1;
                } else if (p1 < p2){
                    return -1;
                } else {
                    return 0;
                }
            }
        });


        String duaDigitTerakhir = "";
        if (seluruhPegawai.size() == 0){
            duaDigitTerakhir += "01";
        }
        else {
            int tempNIPterakhir = Integer.parseInt(seluruhPegawai.get(seluruhPegawai.size()-1).getNip().substring(14,16));
            tempNIPterakhir += 1;
            if ((tempNIPterakhir + "").length() == 1){
                duaDigitTerakhir += "0";
            }
            duaDigitTerakhir += tempNIPterakhir;
        }
//        int tempDuaDigitTerakhir = Integer.parseInt((seluruhPegawai.get((seluruhPegawai.size()-1)).getNip()));


        /*tempDuaDigitTerakhir += 1;
        if ((tempDuaDigitTerakhir+"").length() == 0){
            duaDigitTerakhir += "0" + tempDuaDigitTerakhir;
        }
        else {
            duaDigitTerakhir += tempDuaDigitTerakhir;
        }*/
        String nip = kodeInstansi + tanggal + bulan + tahun + tahunMasuk + duaDigitTerakhir;
        pegawai.setNip(nip);
        pegawaiService.addPegawai(pegawai);
        return "add_pegawai_response";
    }

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
