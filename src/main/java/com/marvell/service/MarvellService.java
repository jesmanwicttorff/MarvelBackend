package com.marvell.service;

import com.marvell.domain.Marvell;
import com.marvell.repository.MarvellRepository;
import com.marvell.service.dto.MarvellDTO;
import com.marvell.service.mapper.MarvellMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marvell.domain.Marvell}.
 */
@Service
@Transactional
public class MarvellService {

    private final Logger log = LoggerFactory.getLogger(MarvellService.class);

    private final MarvellRepository marvellRepository;

    private final MarvellMapper marvellMapper;

    public MarvellService(MarvellRepository marvellRepository, MarvellMapper marvellMapper) {
        this.marvellRepository = marvellRepository;
        this.marvellMapper = marvellMapper;
    }

    /**
     * Save a marvell.
     *
     * @param marvellDTO the entity to save.
     * @return the persisted entity.
     */
    public MarvellDTO save(MarvellDTO marvellDTO) {
        log.debug("Request to save Marvell : {}", marvellDTO);
        Marvell marvell = marvellMapper.toEntity(marvellDTO);
        marvell = marvellRepository.save(marvell);
        return marvellMapper.toDto(marvell);
    }

    /**
     * Update a marvell.
     *
     * @param marvellDTO the entity to save.
     * @return the persisted entity.
     */
    public MarvellDTO update(MarvellDTO marvellDTO) {
        log.debug("Request to update Marvell : {}", marvellDTO);
        Marvell marvell = marvellMapper.toEntity(marvellDTO);
        marvell = marvellRepository.save(marvell);
        return marvellMapper.toDto(marvell);
    }

    /**
     * Partially update a marvell.
     *
     * @param marvellDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MarvellDTO> partialUpdate(MarvellDTO marvellDTO) {
        log.debug("Request to partially update Marvell : {}", marvellDTO);

        return marvellRepository
            .findById(marvellDTO.getId())
            .map(existingMarvell -> {
                marvellMapper.partialUpdate(existingMarvell, marvellDTO);

                return existingMarvell;
            })
            .map(marvellRepository::save)
            .map(marvellMapper::toDto);
    }

    /**
     * Get all the marvells.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MarvellDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Marvells");
        return marvellRepository.findAll(pageable).map(marvellMapper::toDto);
    }

    /**
     * Get one marvell by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MarvellDTO> findOne(Long id) {
        log.debug("Request to get Marvell : {}", id);
        return marvellRepository.findById(id).map(marvellMapper::toDto);
    }

    /**
     * Delete the marvell by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Marvell : {}", id);
        marvellRepository.deleteById(id);
    }
}
