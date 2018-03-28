package com.adias.fap.service;

import com.adias.fap.service.dto.DimensionDTO;
import java.util.List;

/**
 * Service Interface for managing Dimension.
 */
public interface DimensionService {

    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save
     * @return the persisted entity
     */
    DimensionDTO save(DimensionDTO dimensionDTO);

    /**
     * Get all the dimensions.
     *
     * @return the list of entities
     */
    List<DimensionDTO> findAll();

    /**
     * Get the "id" dimension.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DimensionDTO findOne(Long id);

    /**
     * Delete the "id" dimension.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
