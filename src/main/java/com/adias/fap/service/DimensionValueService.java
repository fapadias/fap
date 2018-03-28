package com.adias.fap.service;

import com.adias.fap.service.dto.DimensionValueDTO;
import java.util.List;

/**
 * Service Interface for managing DimensionValue.
 */
public interface DimensionValueService {

    /**
     * Save a dimensionValue.
     *
     * @param dimensionValueDTO the entity to save
     * @return the persisted entity
     */
    DimensionValueDTO save(DimensionValueDTO dimensionValueDTO);

    /**
     * Get all the dimensionValues.
     *
     * @return the list of entities
     */
    List<DimensionValueDTO> findAll();

    /**
     * Get the "id" dimensionValue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DimensionValueDTO findOne(Long id);

    /**
     * Delete the "id" dimensionValue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
