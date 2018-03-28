package com.adias.fap.service.impl;

import com.adias.fap.service.MetricService;
import com.adias.fap.domain.Metric;
import com.adias.fap.repository.MetricRepository;
import com.adias.fap.service.dto.MetricDTO;
import com.adias.fap.service.mapper.MetricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Metric.
 */
@Service
@Transactional
public class MetricServiceImpl implements MetricService {

    private final Logger log = LoggerFactory.getLogger(MetricServiceImpl.class);

    private final MetricRepository metricRepository;

    private final MetricMapper metricMapper;

    public MetricServiceImpl(MetricRepository metricRepository, MetricMapper metricMapper) {
        this.metricRepository = metricRepository;
        this.metricMapper = metricMapper;
    }

    /**
     * Save a metric.
     *
     * @param metricDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MetricDTO save(MetricDTO metricDTO) {
        log.debug("Request to save Metric : {}", metricDTO);
        Metric metric = metricMapper.toEntity(metricDTO);
        metric = metricRepository.save(metric);
        return metricMapper.toDto(metric);
    }

    /**
     * Get all the metrics.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MetricDTO> findAll() {
        log.debug("Request to get all Metrics");
        return metricRepository.findAll().stream()
            .map(metricMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metric by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MetricDTO findOne(Long id) {
        log.debug("Request to get Metric : {}", id);
        Metric metric = metricRepository.findOne(id);
        return metricMapper.toDto(metric);
    }

    /**
     * Delete the metric by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Metric : {}", id);
        metricRepository.delete(id);
    }
}
