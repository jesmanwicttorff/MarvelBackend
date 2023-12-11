package com.marvell.service;

import com.marvell.domain.Zapatos;
import com.marvell.repository.ZapatosRepository;
import com.marvell.service.dto.ZapatosDTO;
import com.marvell.service.mapper.ZapatosMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZapatosService {

    private final Logger log = LoggerFactory.getLogger(ZapatosRepository.class);

    private final ZapatosRepository zapatosRepository;

    private final ZapatosMapper zapatosMapper;

    public ZapatosService(ZapatosRepository zapatosRepository, ZapatosMapper zapatosMapper) {
        this.zapatosRepository = zapatosRepository;
        this.zapatosMapper = zapatosMapper;
    }

    /**
     * Save a zapatos.
     *
     * @param zapatosDTO the entity to save.
     * @return the persisted entity.
     */
    public ZapatosDTO save(ZapatosDTO zapatosDTO) {
        log.debug("Request to save Marvell : {}", zapatosDTO);
        Zapatos zapatos = zapatosMapper.toEntity(zapatosDTO);
        zapatos = zapatosRepository.save(zapatos);
        return zapatosMapper.toDto(zapatos);
    }

    /**
     * Update a marvell.
     *
     * @param zapatosDTO the entity to save.
     * @return the persisted entity.
     */
    public ZapatosDTO update(ZapatosDTO zapatosDTO) {
        log.debug("Request to update Zapatos : {}", zapatosDTO);
        Zapatos zapatos = zapatosMapper.toEntity(zapatosDTO);
        zapatos = zapatosRepository.save(zapatos);
        return zapatosMapper.toDto(zapatos);
    }

    /**
     * Partially update a marvell.
     *
     * @param zapatosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ZapatosDTO> partialUpdate(ZapatosDTO zapatosDTO) {
        log.debug("Request to partially update zapato : {}", zapatosDTO);

        return zapatosRepository
            .findById(zapatosDTO.getId())
            .map(existingZapatos -> {
                zapatosMapper.partialUpdate(existingZapatos, zapatosDTO);

                return existingZapatos;
            })
            .map(zapatosRepository::save)
            .map(zapatosMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ZapatosDTO> listarTodos() {
        log.debug("Request to get all Shoes");
        return zapatosRepository.findAll().stream().map(zapatosMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get one zapatos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ZapatosDTO> buscarPorId(String id) {
        log.debug("Request to get Zapato : {}", id);
        return zapatosRepository.findById(id).map(zapatosMapper::toDto);
    }

    /**
     * Delete the marvell by id.
     *
     * @param id the id of the entity.
     */
    public void deleteShoes(String id) {
        log.debug("Request to delete Marvell : {}", id);
        zapatosRepository.deleteById(id);
    }
}
