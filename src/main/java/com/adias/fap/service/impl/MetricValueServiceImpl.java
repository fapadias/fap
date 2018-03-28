package com.adias.fap.service.impl;

import com.adias.fap.service.MetricValueService;
import com.adias.fap.domain.MetricValue;
import com.adias.fap.repository.MetricValueRepository;
import com.adias.fap.service.dto.MetricValueDTO;
import com.adias.fap.service.mapper.MetricValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MetricValue.
 */
@Service
@Transactional
public class MetricValueServiceImpl implements MetricValueService {

    private final Logger log = LoggerFactory.getLogger(MetricValueServiceImpl.class);

    private final MetricValueRepository metricValueRepository;

    private final MetricValueMapper metricValueMapper;

    public MetricValueServiceImpl(MetricValueRepository metricValueRepository, MetricValueMapper metricValueMapper) {
        this.metricValueRepository = metricValueRepository;
        this.metricValueMapper = metricValueMapper;
    }

    /**
     * Save a metricValue.
     *
     * @param metricValueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MetricValueDTO save(MetricValueDTO metricValueDTO) {
        log.debug("Request to save MetricValue : {}", metricValueDTO);
        MetricValue metricValue = metricValueMapper.toEntity(metricValueDTO);
        metricValue = metricValueRepository.save(metricValue);
        return metricValueMapper.toDto(metricValue);
    }

    /**
     * Get all the metricValues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MetricValueDTO> findAll() {
        log.debug("Request to get all MetricValues");
        return metricValueRepository.findAll().stream()
            .map(metricValueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metricValue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MetricValueDTO findOne(Long id) {
        log.debug("Request to get MetricValue : {}", id);
        MetricValue metricValue = metricValueRepository.findOne(id);
        return metricValueMapper.toDto(metricValue);
    }

    /**
     * Delete the metricValue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MetricValue : {}", id);
        metricValueRepository.delete(id);
    }
}
