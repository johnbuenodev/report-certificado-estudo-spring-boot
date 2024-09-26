package com.buenodev.reportstest.controller;

import com.buenodev.reportstest.entities.Aluno;
import com.buenodev.reportstest.service.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/jasper-report")
public class JasperReportController {

    @Autowired
    private JasperReportService jasperReportService;

    @PostMapping("/gerar-certificado")
    public void gerarCertificado(@RequestBody Aluno aluno) throws IOException {//FileNotFoundException {
        this.jasperReportService.gerar(aluno);
    }


}
