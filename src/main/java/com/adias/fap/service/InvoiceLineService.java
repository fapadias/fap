package com.adias.fap.service;

import com.adias.fap.service.dto.InvoiceLineDTO;
import java.util.List;

/**
 * Service Interface for managing InvoiceLine.
 */
public interface InvoiceLineService {

    /**
     * Save a invoiceLine.
     *
     * @param invoiceLineDTO the entity to save
     * @return the persisted entity
     */
    InvoiceLineDTO save(InvoiceLineDTO invoiceLineDTO);

    /**
     * Get all the invoiceLines.
     *
     * @return the list of entities
     */
    List<InvoiceLineDTO> findAll();

    /**
     * Get the "id" invoiceLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InvoiceLineDTO findOne(Long id);

    /**
     * Delete the "id" invoiceLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
