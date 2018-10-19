package com.apap.tugas1_1606878013.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table (name = "jabatan_pegawai")
public class JabatanPegawaiModel implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    /*@OneToMany
    @JoinColumn(name = "pegawai_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private long idPegawai;

    @OneToMany
    @JoinColumn(name = "jabatan_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private long idJabatan;
*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*public long getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(long idPegawai) {
        this.idPegawai = idPegawai;
    }

    public long getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(long idJabatan) {
        this.idJabatan = idJabatan;
    }*/
}
