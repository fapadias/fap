package com.adias.fap.service.impl;

import com.adias.fap.service.InvoiceLineService;
import com.adias.fap.domain.InvoiceLine;
import com.adias.fap.repository.InvoiceLineRepository;
import com.adias.fap.service.dto.InvoiceLineDTO;
import com.adias.fap.service.mapper.InvoiceLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InvoiceLine.
 */
@Service
@Transactional
public class InvoiceLineServiceImpl implements InvoiceLineService {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineServiceImpl.class);

    private final InvoiceLineRepository invoiceLineRepository;

    private final InvoiceLineMapper invoiceLineMapper;

    public InvoiceLineServiceImpl(InvoiceLineRepository invoiceLineRepository, InvoiceLineMapper invoiceLineMapper) {
        this.invoiceLineRepository = invoiceLineRepository;
        this.invoiceLineMapper = invoiceLineMapper;
    }

    /**
     * Save a invoiceLine.
     *
     * @param invoiceLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InvoiceLineDTO save(InvoiceLineDTO invoiceLineDTO) {
        log.debug("Request to save InvoiceLine : {}", invoiceLineDTO);
        InvoiceLine invoiceLine = invoiceLineMapper.toEntity(invoiceLineDTO);
        invoiceLine = invoiceLineRepository.save(invoiceLine);
        return invoiceLineMapper.toDto(invoiceLine);
    }

    /**
     * Get all the invoiceLines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceLineDTO> findAll() {
        log.debug("Request to get all InvoiceLines");
        return invoiceLineRepository.findAll().stream()
            .map(invoiceLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one invoiceLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InvoiceLineDTO findOne(Long id) {
        log.debug("Request to get InvoiceLine : {}", id);
        InvoiceLine invoiceLine = invoiceLineRepository.findOne(id);
        return invoiceLineMapper.toDto(invoiceLine);
    }

    /**
     * Delete the invoiceLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceLine : {}", id);
        invoiceLineRepository.delete(id);
    }
}
