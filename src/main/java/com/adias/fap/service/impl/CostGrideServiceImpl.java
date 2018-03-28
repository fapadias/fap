package com.adias.fap.service.impl;

import com.adias.fap.service.CostGrideService;
import com.adias.fap.domain.CostGride;
import com.adias.fap.repository.CostGrideRepository;
import com.adias.fap.service.dto.CostGrideDTO;
import com.adias.fap.service.mapper.CostGrideMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CostGride.
 */
@Service
@Transactional
public class CostGrideServiceImpl implements CostGrideService {

    private final Logger log = LoggerFactory.getLogger(CostGrideServiceImpl.class);

    private final CostGrideRepository costGrideRepository;

    private final CostGrideMapper costGrideMapper;

    public CostGrideServiceImpl(CostGrideRepository costGrideRepository, CostGrideMapper costGrideMapper) {
        this.costGrideRepository = costGrideRepository;
        this.costGrideMapper = costGrideMapper;
    }

    /**
     * Save a costGride.
     *
     * @param costGrideDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CostGrideDTO save(CostGrideDTO costGrideDTO) {
        log.debug("Request to save CostGride : {}", costGrideDTO);
        CostGride costGride = costGrideMapper.toEntity(costGrideDTO);
        costGride = costGrideRepository.save(costGride);
        return costGrideMapper.toDto(costGride);
    }

    /**
     * Get all the costGrides.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CostGrideDTO> findAll() {
        log.debug("Request to get all CostGrides");
        return costGrideRepository.findAll().stream()
            .map(costGrideMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one costGride by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CostGrideDTO findOne(Long id) {
        log.debug("Request to get CostGride : {}", id);
        CostGride costGride = costGrideRepository.findOne(id);
        return costGrideMapper.toDto(costGride);
    }

    /**
     * Delete the costGride by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CostGride : {}", id);
        costGrideRepository.delete(id);
    }
}
