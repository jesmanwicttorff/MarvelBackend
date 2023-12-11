package com.marvell.web.rest;

import com.marvell.repository.DistribuidorRepository;
import com.marvell.service.DistribuidorService;
import com.marvell.service.dto.DistribuidorDTO;
import com.marvell.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.marvell.domain.Distribuidor}.
 */
@RestController
@RequestMapping("/api/distribuidors")
public class DistribuidorResource {

    private final Logger log = LoggerFactory.getLogger(DistribuidorResource.class);

    private static final String ENTITY_NAME = "marvellBackDistribuidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistribuidorService distribuidorService;

    private final DistribuidorRepository distribuidorRepository;

    public DistribuidorResource(DistribuidorService distribuidorService, DistribuidorRepository distribuidorRepository) {
        this.distribuidorService = distribuidorService;
        this.distribuidorRepository = distribuidorRepository;
    }

    /**
     * {@code POST  /distribuidors} : Create a new distribuidor.
     *
     * @param distribuidorDTO the distribuidorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distribuidorDTO, or with status {@code 400 (Bad Request)} if the distribuidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DistribuidorDTO> createDistribuidor(@RequestBody DistribuidorDTO distribuidorDTO) throws URISyntaxException {
        log.debug("REST request to save Distribuidor : {}", distribuidorDTO);
        if (distribuidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new distribuidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistribuidorDTO result = distribuidorService.save(distribuidorDTO);
        return ResponseEntity
            .created(new URI("/api/distribuidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distribuidors/:id} : Updates an existing distribuidor.
     *
     * @param id the id of the distribuidorDTO to save.
     * @param distribuidorDTO the distribuidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distribuidorDTO,
     * or with status {@code 400 (Bad Request)} if the distribuidorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distribuidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DistribuidorDTO> updateDistribuidor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistribuidorDTO distribuidorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Distribuidor : {}, {}", id, distribuidorDTO);
        if (distribuidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distribuidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distribuidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistribuidorDTO result = distribuidorService.update(distribuidorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, distribuidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /distribuidors/:id} : Partial updates given fields of an existing distribuidor, field will ignore if it is null
     *
     * @param id the id of the distribuidorDTO to save.
     * @param distribuidorDTO the distribuidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distribuidorDTO,
     * or with status {@code 400 (Bad Request)} if the distribuidorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the distribuidorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the distribuidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DistribuidorDTO> partialUpdateDistribuidor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistribuidorDTO distribuidorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Distribuidor partially : {}, {}", id, distribuidorDTO);
        if (distribuidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distribuidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distribuidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistribuidorDTO> result = distribuidorService.partialUpdate(distribuidorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, distribuidorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /distribuidors} : get all the distribuidors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distribuidors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DistribuidorDTO>> getAllDistribuidors(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Distribuidors");
        Page<DistribuidorDTO> page = distribuidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /distribuidors/:id} : get the "id" distribuidor.
     *
     * @param id the id of the distribuidorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distribuidorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DistribuidorDTO> getDistribuidor(@PathVariable Long id) {
        log.debug("REST request to get Distribuidor : {}", id);
        Optional<DistribuidorDTO> distribuidorDTO = distribuidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distribuidorDTO);
    }

    /**
     * {@code DELETE  /distribuidors/:id} : delete the "id" distribuidor.
     *
     * @param id the id of the distribuidorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistribuidor(@PathVariable Long id) {
        log.debug("REST request to delete Distribuidor : {}", id);
        distribuidorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
