package com.marvell.web.rest;

import com.marvell.repository.ZapatosRepository;
import com.marvell.service.ZapatosService;
import com.marvell.service.dto.ZapatosDTO;
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

@RestController
@RequestMapping("/api")
public class ZapatosResource {

    private final Logger log = LoggerFactory.getLogger(ZapatosResource.class);

    private static final String ENTITY_NAME = "zapatoBackZapato";

    @Value("zapatoBackApp")
    private String applicationName;

    private final ZapatosService zapatosService;

    private final ZapatosRepository zapatosRepository;

    public ZapatosResource(ZapatosService zapatosService, ZapatosRepository zapatosRepository) {
        this.zapatosService = zapatosService;
        this.zapatosRepository = zapatosRepository;
    }

    /**
     * {@code POST  /zapatos} : Create a new marvell.
     *
     * @param zapatosDTO the zapatosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marvellDTO, or with status {@code 400 (Bad Request)} if the marvell has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes/save")
    public ResponseEntity<ZapatosDTO> createMarvell(@RequestBody ZapatosDTO zapatosDTO) throws URISyntaxException {
        log.debug("REST request to save Zapato : {}", zapatosDTO);
        if (zapatosDTO.getId() != null) {
            throw new BadRequestAlertException("A new marvell cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZapatosDTO result = zapatosService.save(zapatosDTO);
        return ResponseEntity
            .created(new URI("/api/shoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zapatos/:id} : Updates an existing marvell.
     *
     * @param id the id of the marvellDTO to save.
     * @param zapatosDTO the zapatosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marvellDTO,
     * or with status {@code 400 (Bad Request)} if the marvellDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marvellDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes/{id}")
    public ResponseEntity<ZapatosDTO> updateMarvell(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ZapatosDTO zapatosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Shoes : {}, {}", id, zapatosDTO);
        if (zapatosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zapatosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zapatosRepository.existsById(String.valueOf(id))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ZapatosDTO result = zapatosService.update(zapatosDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zapatosDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/shoes")
    public List<ZapatosDTO> listarZapatos() {
        log.debug("REST request to get all shoes");
        List<ZapatosDTO> zapatos = zapatosService.listarTodos();
        System.out.println("Tienda " + zapatos);
        if (zapatos.isEmpty()) {
            log.info("No Shoes found in the database");
        } else {
            log.info("Found {} shoes in the database", zapatos.size());
        }
        return zapatos;
    }

    /**
     * {@code GET  /zapato/:id} : get the "id" zapato.
     *
     * @param id the id of the marvellDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marvellDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("shoes/{id}")
    public ResponseEntity<ZapatosDTO> obtenerIdZapato(@PathVariable String id) {
        log.debug("REST request to get Animal : {}", id);
        return zapatosService
            .buscarPorId(id)
            .map(animalDTO -> {
                log.info("Animal found with id: {}", id);
                return ResponseEntity.ok(animalDTO);
            })
            .orElseGet(() -> {
                log.warn("Animal with id {} not found", id);
                return ResponseEntity.notFound().build();
            });
    }

    /**
     * {@code DELETE  /shoes/:id} : delete the "id" marvell.
     *
     * @param id the id of the marvellDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes/{id}")
    public ResponseEntity<Void> deleteShoes(@PathVariable String id) {
        log.debug("REST request to delete Shoes : {}", id);
        zapatosService.deleteShoes(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
