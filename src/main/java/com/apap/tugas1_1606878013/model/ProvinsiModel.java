package com.apap.tugas1_1606878013.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table (name = "provinsi")
public class ProvinsiModel implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Size(max = 10)
    private long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "presentase_tunjangan", nullable = false)
    private Double presentaseTunjangan;

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

    public Double getPresentaseTunjangan() {
        return presentaseTunjangan;
    }

    public void setPresentaseTunjangan(Double presentaseTunjangan) {
        this.presentaseTunjangan = presentaseTunjangan;
    }
}
