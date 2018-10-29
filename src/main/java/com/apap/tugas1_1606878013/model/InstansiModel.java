package com.apap.tugas1_1606878013.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "instansi")
public class InstansiModel implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provinsi", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private ProvinsiModel provinsi;

    @OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PegawaiModel> pegawai;

    public List<PegawaiModel> getPegawai() {
        return pegawai;
    }

    public void setPegawai(List<PegawaiModel> newList) {
        this.pegawai = pegawai;
    }

    public ProvinsiModel getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(ProvinsiModel provinsi) {
        this.provinsi = provinsi;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
