package com.adias.fap.service.impl;

import com.adias.fap.service.InvoiceKeyValueService;
import com.adias.fap.domain.InvoiceKeyValue;
import com.adias.fap.repository.InvoiceKeyValueRepository;
import com.adias.fap.service.dto.InvoiceKeyValueDTO;
import com.adias.fap.service.mapper.InvoiceKeyValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InvoiceKeyValue.
 */
@Service
@Transactional
public class InvoiceKeyValueServiceImpl implements InvoiceKeyValueService {

    private final Logger log = LoggerFactory.getLogger(InvoiceKeyValueServiceImpl.class);

    private final InvoiceKeyValueRepository invoiceKeyValueRepository;

    private final InvoiceKeyValueMapper invoiceKeyValueMapper;

    public InvoiceKeyValueServiceImpl(InvoiceKeyValueRepository invoiceKeyValueRepository, InvoiceKeyValueMapper invoiceKeyValueMapper) {
        this.invoiceKeyValueRepository = invoiceKeyValueRepository;
        this.invoiceKeyValueMapper = invoiceKeyValueMapper;
    }

    /**
     * Save a invoiceKeyValue.
     *
     * @param invoiceKeyValueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InvoiceKeyValueDTO save(InvoiceKeyValueDTO invoiceKeyValueDTO) {
        log.debug("Request to save InvoiceKeyValue : {}", invoiceKeyValueDTO);
        InvoiceKeyValue invoiceKeyValue = invoiceKeyValueMapper.toEntity(invoiceKeyValueDTO);
        invoiceKeyValue = invoiceKeyValueRepository.save(invoiceKeyValue);
        return invoiceKeyValueMapper.toDto(invoiceKeyValue);
    }

    /**
     * Get all the invoiceKeyValues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceKeyValueDTO> findAll() {
        log.debug("Request to get all InvoiceKeyValues");
        return invoiceKeyValueRepository.findAll().stream()
            .map(invoiceKeyValueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one invoiceKeyValue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InvoiceKeyValueDTO findOne(Long id) {
        log.debug("Request to get InvoiceKeyValue : {}", id);
        InvoiceKeyValue invoiceKeyValue = invoiceKeyValueRepository.findOne(id);
        return invoiceKeyValueMapper.toDto(invoiceKeyValue);
    }

    /**
     * Delete the invoiceKeyValue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceKeyValue : {}", id);
        invoiceKeyValueRepository.delete(id);
    }
}
