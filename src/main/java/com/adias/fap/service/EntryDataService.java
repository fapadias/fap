package com.adias.fap.service;

import com.adias.fap.service.dto.EntryDataDTO;
import java.util.List;

/**
 * Service Interface for managing EntryData.
 */
public interface EntryDataService {

    /**
     * Save a entryData.
     *
     * @param entryDataDTO the entity to save
     * @return the persisted entity
     */
    EntryDataDTO save(EntryDataDTO entryDataDTO);

    /**
     * Get all the entryData.
     *
     * @return the list of entities
     */
    List<EntryDataDTO> findAll();

    /**
     * Get the "id" entryData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EntryDataDTO findOne(Long id);

    /**
     * Delete the "id" entryData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
