package com.apap.tugas1_1606878013.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "provinsi")
public class ProvinsiModel implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
}
