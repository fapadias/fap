package com.adias.fap.service.impl;

import com.adias.fap.service.DimensionValueService;
import com.adias.fap.domain.DimensionValue;
import com.adias.fap.repository.DimensionValueRepository;
import com.adias.fap.service.dto.DimensionValueDTO;
import com.adias.fap.service.mapper.DimensionValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DimensionValue.
 */
@Service
@Transactional
public class DimensionValueServiceImpl implements DimensionValueService {

    private final Logger log = LoggerFactory.getLogger(DimensionValueServiceImpl.class);

    private final DimensionValueRepository dimensionValueRepository;

    private final DimensionValueMapper dimensionValueMapper;

    public DimensionValueServiceImpl(DimensionValueRepository dimensionValueRepository, DimensionValueMapper dimensionValueMapper) {
        this.dimensionValueRepository = dimensionValueRepository;
        this.dimensionValueMapper = dimensionValueMapper;
    }

    /**
     * Save a dimensionValue.
     *
     * @param dimensionValueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DimensionValueDTO save(DimensionValueDTO dimensionValueDTO) {
        log.debug("Request to save DimensionValue : {}", dimensionValueDTO);
        DimensionValue dimensionValue = dimensionValueMapper.toEntity(dimensionValueDTO);
        dimensionValue = dimensionValueRepository.save(dimensionValue);
        return dimensionValueMapper.toDto(dimensionValue);
    }

    /**
     * Get all the dimensionValues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DimensionValueDTO> findAll() {
        log.debug("Request to get all DimensionValues");
        return dimensionValueRepository.findAll().stream()
            .map(dimensionValueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dimensionValue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DimensionValueDTO findOne(Long id) {
        log.debug("Request to get DimensionValue : {}", id);
        DimensionValue dimensionValue = dimensionValueRepository.findOne(id);
        return dimensionValueMapper.toDto(dimensionValue);
    }

    /**
     * Delete the dimensionValue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DimensionValue : {}", id);
        dimensionValueRepository.delete(id);
    }
}
