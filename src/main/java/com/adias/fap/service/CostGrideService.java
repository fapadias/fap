package com.adias.fap.service;

import com.adias.fap.service.dto.CostGrideDTO;
import java.util.List;

/**
 * Service Interface for managing CostGride.
 */
public interface CostGrideService {

    /**
     * Save a costGride.
     *
     * @param costGrideDTO the entity to save
     * @return the persisted entity
     */
    CostGrideDTO save(CostGrideDTO costGrideDTO);

    /**
     * Get all the costGrides.
     *
     * @return the list of entities
     */
    List<CostGrideDTO> findAll();

    /**
     * Get the "id" costGride.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CostGrideDTO findOne(Long id);

    /**
     * Delete the "id" costGride.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
