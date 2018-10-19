package com.apap.tugas1_1606878013.controller;

import com.apap.tugas1_1606878013.model.JabatanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JabatanController {
    @Autowired
    private JabatanService jabatanService;

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
}
