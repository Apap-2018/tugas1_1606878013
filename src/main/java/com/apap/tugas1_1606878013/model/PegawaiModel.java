package com.apap.tugas1_1606878013.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.sql.Date;

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "NIP", nullable = false, unique = true)
    private String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "tempat_lahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Column(name = "tanggal_lahir", nullable = false)
    private java.sql.Date tanggalLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "tahun_masuk", nullable = false)
    private String tahunMasuk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private InstansiModel instansi;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "jabatan_pegawai",  joinColumns = { @JoinColumn(name = "id_pegawai") },
            inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
    private List<JabatanModel> jabatanPegawai;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public java.sql.Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(java.sql.Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public InstansiModel getInstansi() {
        return instansi;
    }

    public void setInstansi(InstansiModel instansi) {
        this.instansi = instansi;
    }

    public List<JabatanModel> getJabatanPegawai() {
        return jabatanPegawai;
    }

    public void addJabatan(JabatanModel jabatanModel){
        jabatanPegawai.add(jabatanModel);
    }

    public void setJabatanPegawai(List<JabatanModel> jabatanPegawai) {
        this.jabatanPegawai = jabatanPegawai;
    }

    public Double hitungGajiPokokTerbesar(){
        double result = 0;

        Double presentaseTunjangan = getInstansi().getProvinsi().getPresentaseTunjangan();

        for (int i = 0 ; i < jabatanPegawai.size() ; i++){
            Double tempGajiPokok = jabatanPegawai.get(i).getGajiPokok();
            Double tempTunjangan = presentaseTunjangan * jabatanPegawai.get(i).getGajiPokok();
            Double tempTotal = tempGajiPokok + tempTunjangan;
            if (tempTotal > result){
                result = tempTotal;
            }
        }

        return result;
    }

    public int getUmur(){
        long ageInMillis = new java.util.Date().getTime() - getTanggalLahir().getTime();
        java.util.Date age = new java.util.Date(ageInMillis);
        return age.getYear();
    }
}
