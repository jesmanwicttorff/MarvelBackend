package com.marvell.service;

import com.marvell.domain.Distribuidor;
import com.marvell.repository.DistribuidorRepository;
import com.marvell.service.dto.DistribuidorDTO;
import com.marvell.service.mapper.DistribuidorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marvell.domain.Distribuidor}.
 */
@Service
@Transactional
public class DistribuidorService {

    private final Logger log = LoggerFactory.getLogger(DistribuidorService.class);

    private final DistribuidorRepository distribuidorRepository;

    private final DistribuidorMapper distribuidorMapper;

    public DistribuidorService(DistribuidorRepository distribuidorRepository, DistribuidorMapper distribuidorMapper) {
        this.distribuidorRepository = distribuidorRepository;
        this.distribuidorMapper = distribuidorMapper;
    }

    /**
     * Save a distribuidor.
     *
     * @param distribuidorDTO the entity to save.
     * @return the persisted entity.
     */
    public DistribuidorDTO save(DistribuidorDTO distribuidorDTO) {
        log.debug("Request to save Distribuidor : {}", distribuidorDTO);
        Distribuidor distribuidor = distribuidorMapper.toEntity(distribuidorDTO);
        distribuidor = distribuidorRepository.save(distribuidor);
        return distribuidorMapper.toDto(distribuidor);
    }

    /**
     * Update a distribuidor.
     *
     * @param distribuidorDTO the entity to save.
     * @return the persisted entity.
     */
    public DistribuidorDTO update(DistribuidorDTO distribuidorDTO) {
        log.debug("Request to update Distribuidor : {}", distribuidorDTO);
        Distribuidor distribuidor = distribuidorMapper.toEntity(distribuidorDTO);
        distribuidor = distribuidorRepository.save(distribuidor);
        return distribuidorMapper.toDto(distribuidor);
    }

    /**
     * Partially update a distribuidor.
     *
     * @param distribuidorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DistribuidorDTO> partialUpdate(DistribuidorDTO distribuidorDTO) {
        log.debug("Request to partially update Distribuidor : {}", distribuidorDTO);

        return distribuidorRepository
            .findById(distribuidorDTO.getId())
            .map(existingDistribuidor -> {
                distribuidorMapper.partialUpdate(existingDistribuidor, distribuidorDTO);

                return existingDistribuidor;
            })
            .map(distribuidorRepository::save)
            .map(distribuidorMapper::toDto);
    }

    /**
     * Get all the distribuidors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DistribuidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Distribuidors");
        return distribuidorRepository.findAll(pageable).map(distribuidorMapper::toDto);
    }

    /**
     * Get one distribuidor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DistribuidorDTO> findOne(Long id) {
        log.debug("Request to get Distribuidor : {}", id);
        return distribuidorRepository.findById(id).map(distribuidorMapper::toDto);
    }

    /**
     * Delete the distribuidor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Distribuidor : {}", id);
        distribuidorRepository.deleteById(id);
    }
}
