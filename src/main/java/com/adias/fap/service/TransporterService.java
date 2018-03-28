package com.adias.fap.service;

import com.adias.fap.service.dto.TransporterDTO;
import java.util.List;

/**
 * Service Interface for managing Transporter.
 */
public interface TransporterService {

    /**
     * Save a transporter.
     *
     * @param transporterDTO the entity to save
     * @return the persisted entity
     */
    TransporterDTO save(TransporterDTO transporterDTO);

    /**
     * Get all the transporters.
     *
     * @return the list of entities
     */
    List<TransporterDTO> findAll();

    /**
     * Get the "id" transporter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TransporterDTO findOne(Long id);

    /**
     * Delete the "id" transporter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
