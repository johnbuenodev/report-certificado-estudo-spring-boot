package com.buenodev.reportstest.service;

import com.buenodev.reportstest.entities.Aluno;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


@Service
public class JasperReportService {

    public static final String CERTIFICADOS = "classpath:jasper/certificados/";

    public static final String IMAGEBG = "classpath:jasper/img/jasper-img.png";

    public static final String ARQUIVO_XRML = "certificadoTest.jrxml";

    public static final Logger LOGGER = LoggerFactory.getLogger(JasperReportService.class);

    public static final String DESTINO_PDF = "C:\\jasper-report";

    public void gerar(Aluno aluno) throws IOException {

        byte[] imageByte = this.loadingImage(IMAGEBG);

        Map<String, Object> params = new HashMap<>();
        params.put("nome", aluno.getNome());
        params.put("curso", aluno.getCurso());
        params.put("cargaHoraria", aluno.getCargaHoraria());
        params.put("dataInicioCurso", aluno.getDataInicioCurso());
        params.put("dataTerminoCurso", aluno.getDataTerminoCurso());
        params.put("imageJasper", imageByte);

        String pathFinalAbsolute = getAbsolutePathCustom();

        try {
            //local de armazenamento
            String folderDiretorio = getDiretorioSavePDF("certificados-salvos");

            JasperReport report = JasperCompileManager.compileReport(pathFinalAbsolute);
            LOGGER.info("report compilado: {}", pathFinalAbsolute);

            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            LOGGER.info("jasper print");

            //exportar o PDF JasperExportManager exportPDF
            JasperExportManager.exportReportToPdfFile(print, folderDiretorio);
            LOGGER.info("PDF exportado para diretorio: {}", folderDiretorio);

        } catch (JRException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    //IO exception para tratar corretamente
    private byte[] loadingImage(String s) throws IOException {
        String image = ResourceUtils.getFile(s).getAbsolutePath();

        File file = new File(image);

        try(InputStream inputStream = new FileInputStream(file)) {

            return IOUtils.toByteArray(inputStream);

        }

    }

    private String getDiretorioSavePDF(String pathSaveFile) {
        this.criarDiretorio(DESTINO_PDF);
        return DESTINO_PDF+"\\"+pathSaveFile.concat(".pdf");
    }

    private void criarDiretorio(String name) {
        File dir = new File(name);
        if(!dir.exists()) {
          dir.mkdir();
        }
    }

    private String getAbsolutePathCustom() throws FileNotFoundException {
        return ResourceUtils.getFile(CERTIFICADOS+ARQUIVO_XRML).getAbsolutePath();
    }
}


/*

POST

http://localhost:8080/jasper-report/gerar-certificado

Headers   Content-Type  application/json

json para testar

{
"nome":"john",
"curso": "analise e desenvolvimento sistemas",
"cargaHoraria": 300,
"dataInicioCurso": "2023-03-02T14:30:00Z",
"dataTerminoCurso": "2024-03-02T14:30:00Z"
}

 */