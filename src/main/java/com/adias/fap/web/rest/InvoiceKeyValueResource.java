package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.InvoiceKeyValueService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.InvoiceKeyValueDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvoiceKeyValue.
 */
@RestController
@RequestMapping("/api")
public class InvoiceKeyValueResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceKeyValueResource.class);

    private static final String ENTITY_NAME = "invoiceKeyValue";

    private final InvoiceKeyValueService invoiceKeyValueService;

    public InvoiceKeyValueResource(InvoiceKeyValueService invoiceKeyValueService) {
        this.invoiceKeyValueService = invoiceKeyValueService;
    }

    /**
     * POST  /invoice-key-values : Create a new invoiceKeyValue.
     *
     * @param invoiceKeyValueDTO the invoiceKeyValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceKeyValueDTO, or with status 400 (Bad Request) if the invoiceKeyValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-key-values")
    @Timed
    public ResponseEntity<InvoiceKeyValueDTO> createInvoiceKeyValue(@RequestBody InvoiceKeyValueDTO invoiceKeyValueDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceKeyValue : {}", invoiceKeyValueDTO);
        if (invoiceKeyValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceKeyValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceKeyValueDTO result = invoiceKeyValueService.save(invoiceKeyValueDTO);
        return ResponseEntity.created(new URI("/api/invoice-key-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-key-values : Updates an existing invoiceKeyValue.
     *
     * @param invoiceKeyValueDTO the invoiceKeyValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceKeyValueDTO,
     * or with status 400 (Bad Request) if the invoiceKeyValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceKeyValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-key-values")
    @Timed
    public ResponseEntity<InvoiceKeyValueDTO> updateInvoiceKeyValue(@RequestBody InvoiceKeyValueDTO invoiceKeyValueDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceKeyValue : {}", invoiceKeyValueDTO);
        if (invoiceKeyValueDTO.getId() == null) {
            return createInvoiceKeyValue(invoiceKeyValueDTO);
        }
        InvoiceKeyValueDTO result = invoiceKeyValueService.save(invoiceKeyValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceKeyValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-key-values : get all the invoiceKeyValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceKeyValues in body
     */
    @GetMapping("/invoice-key-values")
    @Timed
    public List<InvoiceKeyValueDTO> getAllInvoiceKeyValues() {
        log.debug("REST request to get all InvoiceKeyValues");
        return invoiceKeyValueService.findAll();
        }

    /**
     * GET  /invoice-key-values/:id : get the "id" invoiceKeyValue.
     *
     * @param id the id of the invoiceKeyValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceKeyValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-key-values/{id}")
    @Timed
    public ResponseEntity<InvoiceKeyValueDTO> getInvoiceKeyValue(@PathVariable Long id) {
        log.debug("REST request to get InvoiceKeyValue : {}", id);
        InvoiceKeyValueDTO invoiceKeyValueDTO = invoiceKeyValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invoiceKeyValueDTO));
    }

    /**
     * DELETE  /invoice-key-values/:id : delete the "id" invoiceKeyValue.
     *
     * @param id the id of the invoiceKeyValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-key-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceKeyValue(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceKeyValue : {}", id);
        invoiceKeyValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
