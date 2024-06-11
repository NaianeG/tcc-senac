package com.tcc.aplicacao.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class JasperService {

    private static final String JASPER_DIRETORIO = "classpath:jasper/";
    private static final String JASPER_PREFIXO = "Rel0";
    private static final String JASPER_SUFIXO = ".jasper";

    @Autowired
    private Connection connection;

    @Autowired
    ResourceLoader resourceLoader;

    private Map<String, Object> params = new HashMap<>();

    public JasperService() {
        this.params.put("REPORT_LOCALE", new Locale("pt", "BR"));

    }

    public void addParams(String key, Object value) {
        this.params.put(key, value);
    }

    public byte[] exportarPDF(String code) {
        byte[] bytes = null;
        try {
            Resource resource = resourceLoader
                    .getResource(JASPER_DIRETORIO.concat(JASPER_PREFIXO).concat(code).concat(JASPER_SUFIXO));
            InputStream stream = resource.getInputStream();

            JasperPrint print = JasperFillManager.fillReport(stream, params, connection);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public JRXlsxExporter gerarEXCEL(String code, HttpServletResponse response) {
        JRXlsxExporter exporter = null;
        try {
            File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(JASPER_PREFIXO)
                    .concat(code)
                    .concat(JASPER_SUFIXO));

            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);

            exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));

            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            exporter.setConfiguration(reportConfigXLS);

            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

        } catch (JRException | IOException e) {
            e.printStackTrace();
        }

        return exporter;
    }
}
