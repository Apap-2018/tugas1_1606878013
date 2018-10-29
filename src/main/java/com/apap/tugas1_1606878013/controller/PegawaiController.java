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

import java.text.DecimalFormat;
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
        DecimalFormat df = new DecimalFormat("#,###");
        model.addAttribute("gajiPokok", df.format(gajiPokokTerbesar));
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

        System.out.println(tanggal);

        int bulan = pegawai.getTanggalLahir().getMonth() + 1;

        System.out.println(bulan);

        int tahun = pegawai.getTanggalLahir().getYear();

        System.out.println(tahun);

//        String tahun = (tempTahun + "").substring(2,4);

        long kodeInstansi = pegawai.getInstansi().getId();
        System.out.println(kodeInstansi);

        String tahunMasuk = pegawai.getTahunMasuk();
        System.out.println(tahunMasuk);

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

    @GetMapping (value = "pegawai/ubah")
    private String update (@RequestParam("nip") String nip, Model model){
        PegawaiModel pegawai = pegawaiService.findPegawaiByNip(nip);
        model.addAttribute("pegawai", pegawai);

        List<ProvinsiModel> daftarProvinsi = provinsiDb.findAll();
        List<InstansiModel> daftarInstansi = instansiDb.findAll();
        List<JabatanModel> daftarJabatan = jabatanDb.findAll();

        InstansiModel instansiPegawaiSaatIni = pegawai.getInstansi();
        ProvinsiModel provinsiPegawaiSaatIni = pegawai.getInstansi().getProvinsi();


        List<InstansiModel> daftarSeluruhInstansiDiProvinsiPegawaiSaatIni = new ArrayList<InstansiModel>();
        for (InstansiModel e : daftarInstansi){
            if (e.getProvinsi().getId() == provinsiPegawaiSaatIni.getId()){
                daftarSeluruhInstansiDiProvinsiPegawaiSaatIni.add(e);
            }
        }

        JabatanModel jabatanPegawaiSaatIni = pegawai.getJabatanPegawai().get(0);

        model.addAttribute("instansiPegawai", instansiPegawaiSaatIni);
        model.addAttribute("provinsiInstansiPegawai", provinsiPegawaiSaatIni);
        model.addAttribute("daftarSeluruhInstansiDiProvinsiPegawai", daftarSeluruhInstansiDiProvinsiPegawaiSaatIni);
        model.addAttribute("jabatanPegawai", jabatanPegawaiSaatIni);
        model.addAttribute("allProvinsi", daftarProvinsi);
        model.addAttribute("allJabatan", daftarJabatan);

        return "change_pegawai";
    }

    @PostMapping (value = "pegawai/ubah")
    private String change(@ModelAttribute PegawaiModel updatedPegawai, Model model){
        System.out.println(updatedPegawai.getId());
        System.out.println(updatedPegawai.getNip());
        System.out.println(updatedPegawai.getJabatanPegawai());
        System.out.println(updatedPegawai.getInstansi());
        System.out.println(updatedPegawai.getTempatLahir());
        System.out.println(updatedPegawai.getTanggalLahir());
        System.out.println(updatedPegawai.getTahunMasuk());
        System.out.println(updatedPegawai.getNama());
        int tempTanggal = updatedPegawai.getTanggalLahir().getDate();
        String tanggal = "";
        if ((tempTanggal + "").length() == 1){
            tanggal = "0" + tempTanggal;
        }

        System.out.println(tanggal);

        int bulan = updatedPegawai.getTanggalLahir().getMonth() + 1;

        System.out.println(bulan);

        int tahun = updatedPegawai.getTanggalLahir().getYear();

        System.out.println(tahun);

//        String tahun = (tempTahun + "").substring(2,4);

        long kodeInstansi = updatedPegawai.getInstansi().getId();
        System.out.println(kodeInstansi);

        String tahunMasuk = updatedPegawai.getTahunMasuk();
        System.out.println(tahunMasuk);

        List<PegawaiModel> seluruhPegawai = pegawaiDb.findByTahunMasukAndTanggalLahir(updatedPegawai.getTahunMasuk(), updatedPegawai.getTanggalLahir());


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
        updatedPegawai.setNip(nip);

        PegawaiModel changingPegawai = pegawaiDb.getOne(updatedPegawai.getId());
        changingPegawai.setNip(updatedPegawai.getNip());
        changingPegawai.setJabatanPegawai(updatedPegawai.getJabatanPegawai());
        changingPegawai.setInstansi(updatedPegawai.getInstansi());
        changingPegawai.setTahunMasuk(updatedPegawai.getTahunMasuk());
        changingPegawai.setTanggalLahir(updatedPegawai.getTanggalLahir());
        changingPegawai.setTempatLahir(updatedPegawai.getTempatLahir());
        changingPegawai.setNama(updatedPegawai.getNama());
        pegawaiService.updatePegawai(changingPegawai);

        return "change_pegawai_response";
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

    @GetMapping (value = "pegawai/cari")
    private String search(Model model){
        model.addAttribute("allPegawai", pegawaiDb.findAll());
        model.addAttribute("allInstansi", instansiDb.findAll());
        model.addAttribute("allProvinsi", provinsiDb.findAll());
        model.addAttribute("allJabatan", jabatanDb.findAll());
        return "search_pegawai";
    }

    /*@PostMapping
//    @GetMapping(value = "pegawai/ubah")
//    private String updatePegawai ()
*/
}
