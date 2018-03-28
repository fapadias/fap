package com.adias.fap.service.impl;

import com.adias.fap.service.DimensionService;
import com.adias.fap.domain.Dimension;
import com.adias.fap.repository.DimensionRepository;
import com.adias.fap.service.dto.DimensionDTO;
import com.adias.fap.service.mapper.DimensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Dimension.
 */
@Service
@Transactional
public class DimensionServiceImpl implements DimensionService {

    private final Logger log = LoggerFactory.getLogger(DimensionServiceImpl.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionMapper dimensionMapper;

    public DimensionServiceImpl(DimensionRepository dimensionRepository, DimensionMapper dimensionMapper) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionMapper = dimensionMapper;
    }

    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DimensionDTO save(DimensionDTO dimensionDTO) {
        log.debug("Request to save Dimension : {}", dimensionDTO);
        Dimension dimension = dimensionMapper.toEntity(dimensionDTO);
        dimension = dimensionRepository.save(dimension);
        return dimensionMapper.toDto(dimension);
    }

    /**
     * Get all the dimensions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DimensionDTO> findAll() {
        log.debug("Request to get all Dimensions");
        return dimensionRepository.findAll().stream()
            .map(dimensionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dimension by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DimensionDTO findOne(Long id) {
        log.debug("Request to get Dimension : {}", id);
        Dimension dimension = dimensionRepository.findOne(id);
        return dimensionMapper.toDto(dimension);
    }

    /**
     * Delete the dimension by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dimension : {}", id);
        dimensionRepository.delete(id);
    }
}
