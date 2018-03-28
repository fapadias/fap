package com.adias.fap.service.impl;

import com.adias.fap.service.EntryDataService;
import com.adias.fap.domain.EntryData;
import com.adias.fap.repository.EntryDataRepository;
import com.adias.fap.service.dto.EntryDataDTO;
import com.adias.fap.service.mapper.EntryDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EntryData.
 */
@Service
@Transactional
public class EntryDataServiceImpl implements EntryDataService {

    private final Logger log = LoggerFactory.getLogger(EntryDataServiceImpl.class);

    private final EntryDataRepository entryDataRepository;

    private final EntryDataMapper entryDataMapper;

    public EntryDataServiceImpl(EntryDataRepository entryDataRepository, EntryDataMapper entryDataMapper) {
        this.entryDataRepository = entryDataRepository;
        this.entryDataMapper = entryDataMapper;
    }

    /**
     * Save a entryData.
     *
     * @param entryDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntryDataDTO save(EntryDataDTO entryDataDTO) {
        log.debug("Request to save EntryData : {}", entryDataDTO);
        EntryData entryData = entryDataMapper.toEntity(entryDataDTO);
        entryData = entryDataRepository.save(entryData);
        return entryDataMapper.toDto(entryData);
    }

    /**
     * Get all the entryData.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EntryDataDTO> findAll() {
        log.debug("Request to get all EntryData");
        return entryDataRepository.findAll().stream()
            .map(entryDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entryData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntryDataDTO findOne(Long id) {
        log.debug("Request to get EntryData : {}", id);
        EntryData entryData = entryDataRepository.findOne(id);
        return entryDataMapper.toDto(entryData);
    }

    /**
     * Delete the entryData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntryData : {}", id);
        entryDataRepository.delete(id);
    }
}
