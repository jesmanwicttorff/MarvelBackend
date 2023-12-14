package com.marvell.service;

import com.marvell.domain.Marvell;
import com.marvell.repository.MarvellRepository;
import com.marvell.service.dto.MarvellDTO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class ReportService {

    @Autowired
    private MarvellRepository marvellRepository;

    private MarvellService marvellService;

    private final DataSource dataSource;

    public ReportService(DataSource dataSource, MarvellService marvellService) {
        this.dataSource = dataSource;
        this.marvellService = marvellService;
    }

    public String expoReport(String reportFormat) throws FileNotFoundException, JRException {
        List<Marvell> marvell = marvellRepository.findAll();
        System.out.println("Marvell JEAW " + marvell);

        String path = "C:\\Users\\Globalsym\\Desktop\\\\migrationKeycloack\\marvelFront\\report";
        // Cargo el archivo
        File file = ResourceUtils.getFile("classpath:reporte/UniversoMarvell.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(marvell);
        parameters.put("createdby", "Universo Marvell");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\UniversoMarvell.html");
        }

        if (reportFormat.equalsIgnoreCase(("pdf"))) {

            arrayOutputStream.reset();
            JRPdfExporter exporterPdf = new JRPdfExporter();
            exporterPdf.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(arrayOutputStream));
            exporterPdf.exportReport();
            System.out.println("Document ");

        }
        return  Base64.getEncoder().encodeToString(arrayOutputStream.toByteArray());

    }

    public String ExcelReportMarvell() throws Exception {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<MarvellDTO> marvellDTOS = marvellService.findAll(pageable).getContent();
        File file = ResourceUtils.getFile("classpath:reporte/UniversoMarvell.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(marvellDTOS));

            JRXlsxExporter exporter = new JRXlsxExporter();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            return Base64.getEncoder().encodeToString(os.toByteArray());

    }

    public String WordReportMarvell() throws Exception {
        File file = ResourceUtils.getFile("classpath:reporte/marvell.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream(file));
        Map<String, Object> parameters = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JRDocxExporter exporter = new JRDocxExporter();
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));

            SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
            exporter.setConfiguration(configuration);

            exporter.exportReport();
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
    }
}
