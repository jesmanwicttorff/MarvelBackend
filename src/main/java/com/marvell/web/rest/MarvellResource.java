package com.marvell.web.rest;

import com.marvell.repository.MarvellRepository;
import com.marvell.service.MarvellService;
import com.marvell.service.ReportService;
import com.marvell.service.dto.MarvellDTO;
import com.marvell.web.rest.errors.BadRequestAlertException;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import feign.Body;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.marvell.domain.Marvell}.
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(value = "localhost:3000")
public class MarvellResource {

    private final Logger log = LoggerFactory.getLogger(MarvellResource.class);

    private static final String ENTITY_NAME = "marvellBackMarvell";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarvellService marvellService;

    private final MarvellRepository marvellRepository;

    public MarvellResource(MarvellService marvellService, MarvellRepository marvellRepository, ReportService reportService) {
        this.marvellService = marvellService;
        this.marvellRepository = marvellRepository;
        this.reportService = reportService;
    }

    private final ReportService reportService;

    /**
     * {@code POST  /marvells} : Create a new marvell.
     *
     * @param marvellDTO the marvellDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marvellDTO, or with status {@code 400 (Bad Request)} if the marvell has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marvell/save")
    public ResponseEntity<MarvellDTO> createMarvell(@RequestBody MarvellDTO marvellDTO) throws URISyntaxException {
        log.debug("REST request to save Marvell : {}", marvellDTO);
        if (marvellDTO.getId() != null) {
            throw new BadRequestAlertException("A new marvell cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarvellDTO result = marvellService.save(marvellDTO);
        return ResponseEntity
            .created(new URI("/api/marvells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marvells/:id} : Updates an existing marvell.
     *
     * @param id the id of the marvellDTO to save.
     * @param marvellDTO the marvellDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marvellDTO,
     * or with status {@code 400 (Bad Request)} if the marvellDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marvellDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marvell/{id}")
    public ResponseEntity<MarvellDTO> updateMarvell(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MarvellDTO marvellDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Marvell : {}, {}", id, marvellDTO);
        if (marvellDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marvellDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marvellRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarvellDTO result = marvellService.update(marvellDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marvellDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /marvells} : get all the marvells.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marvells in body.
     */
    @GetMapping("/marvell")
    public ResponseEntity<List<MarvellDTO>> getAllMarvells(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Marvells");
        Page<MarvellDTO> page = marvellService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marvells/:id} : get the "id" marvell.
     *
     * @param id the id of the marvellDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marvellDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marvell/{id}")
    public ResponseEntity<MarvellDTO> getMarvell(@PathVariable Long id) {
        log.debug("REST request to get Marvell : {}", id);
        Optional<MarvellDTO> marvellDTO = marvellService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marvellDTO);
    }

    /**
     * {@code DELETE  /marvells/:id} : delete the "id" marvell.
     *
     * @param id the id of the marvellDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marvell/{id}")
    public ResponseEntity<Void> deleteMarvell(@PathVariable Long id) {
        log.debug("REST request to delete Marvell : {}", id);
        marvellService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    // Defino el servicio para generar el archivo PDF y HTML
    @GetMapping("marvell/report/{format}")
    public  ResponseEntity<Void> getPdfReport(@PathVariable String format, HttpServletResponse response) {
        log.debug("REST request to get a PDF report");
        try {

            String report = reportService.expoReport(format);
            System.out.println("PDF " + report);

            response.setHeader("Content-Disposition", "inline; filename=\"" + "ReportNombre" + "\"");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("reportName", "ReportNombre");

            response.setHeader("output_content", "application/pdf");
            response.setHeader("output_extension", "." + "pdf");
            response.setContentType("application/pdf");
            byte[] contentBytes = Base64.getDecoder().decode(report);
            ByteArrayInputStream inStream = new ByteArrayInputStream(contentBytes);
            IOUtils.copy(inStream, response.getOutputStream());
           // System.out.println("Respuesta " + IOUtils.copy(inStream, response.getOutputStream()));

            return ResponseEntity.ok()
                .body(null);

        } catch (Exception e) {
            log.error("REST request to get a PDF report failed: {}", e.getMessage());
            return ResponseEntity
                .internalServerError()
                .headers(
                    HeaderUtil.createFailureAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        "pdfReportNotGenerated",
                        "There was an error generating the PDF report"
                    )
                )
                .build();
        }
    }

    @GetMapping("marvell/word")
    public ResponseEntity<String> getWord() {
        log.debug("REST request to get a Word report");
        try {
            String report = reportService.WordReportMarvell();
            System.out.println("Here word" + report);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "wordReportGenerated"))
                .body(report);
        } catch (Exception e) {
            log.error("REST request to get a Word report failed: {}", e.getMessage());
            return ResponseEntity
                .internalServerError()
                .headers(
                    HeaderUtil.createFailureAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        "wordReportNotGenerated",
                        "There was an error generating the Word report"
                    )
                )
                .build();
        }
    }

    @GetMapping("marvell/excel")
    public ResponseEntity<String> getExcel() {
        log.debug("REST request to get an Excel report");
        try {
            String report = reportService.ExcelReportMarvell();
            System.out.println("Lego aqui " + report);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "excelReportGenerated"))
                .body(report);
        } catch (Exception e) {
            log.error("REST request to get an Excel report failed: {}", e.getMessage());
            return ResponseEntity
                .internalServerError()
                .headers(
                    HeaderUtil.createFailureAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        "excelReportNotGenerated",
                        "There was an error generating the Excel report"
                    )
                )
                .build();
        }
    }
}
