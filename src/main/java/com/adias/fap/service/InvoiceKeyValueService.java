package com.adias.fap.service;

import com.adias.fap.service.dto.InvoiceKeyValueDTO;
import java.util.List;

/**
 * Service Interface for managing InvoiceKeyValue.
 */
public interface InvoiceKeyValueService {

    /**
     * Save a invoiceKeyValue.
     *
     * @param invoiceKeyValueDTO the entity to save
     * @return the persisted entity
     */
    InvoiceKeyValueDTO save(InvoiceKeyValueDTO invoiceKeyValueDTO);

    /**
     * Get all the invoiceKeyValues.
     *
     * @return the list of entities
     */
    List<InvoiceKeyValueDTO> findAll();

    /**
     * Get the "id" invoiceKeyValue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InvoiceKeyValueDTO findOne(Long id);

    /**
     * Delete the "id" invoiceKeyValue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
