package com.adias.fap.service;

import com.adias.fap.service.dto.MetricValueDTO;
import java.util.List;

/**
 * Service Interface for managing MetricValue.
 */
public interface MetricValueService {

    /**
     * Save a metricValue.
     *
     * @param metricValueDTO the entity to save
     * @return the persisted entity
     */
    MetricValueDTO save(MetricValueDTO metricValueDTO);

    /**
     * Get all the metricValues.
     *
     * @return the list of entities
     */
    List<MetricValueDTO> findAll();

    /**
     * Get the "id" metricValue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MetricValueDTO findOne(Long id);

    /**
     * Delete the "id" metricValue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
