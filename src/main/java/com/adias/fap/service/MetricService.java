package com.adias.fap.service;

import com.adias.fap.service.dto.MetricDTO;
import java.util.List;

/**
 * Service Interface for managing Metric.
 */
public interface MetricService {

    /**
     * Save a metric.
     *
     * @param metricDTO the entity to save
     * @return the persisted entity
     */
    MetricDTO save(MetricDTO metricDTO);

    /**
     * Get all the metrics.
     *
     * @return the list of entities
     */
    List<MetricDTO> findAll();

    /**
     * Get the "id" metric.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MetricDTO findOne(Long id);

    /**
     * Delete the "id" metric.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
