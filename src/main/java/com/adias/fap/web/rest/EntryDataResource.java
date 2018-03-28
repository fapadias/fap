package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.EntryDataService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.EntryDataDTO;
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
 * REST controller for managing EntryData.
 */
@RestController
@RequestMapping("/api")
public class EntryDataResource {

    private final Logger log = LoggerFactory.getLogger(EntryDataResource.class);

    private static final String ENTITY_NAME = "entryData";

    private final EntryDataService entryDataService;

    public EntryDataResource(EntryDataService entryDataService) {
        this.entryDataService = entryDataService;
    }

    /**
     * POST  /entry-data : Create a new entryData.
     *
     * @param entryDataDTO the entryDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entryDataDTO, or with status 400 (Bad Request) if the entryData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entry-data")
    @Timed
    public ResponseEntity<EntryDataDTO> createEntryData(@RequestBody EntryDataDTO entryDataDTO) throws URISyntaxException {
        log.debug("REST request to save EntryData : {}", entryDataDTO);
        if (entryDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new entryData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntryDataDTO result = entryDataService.save(entryDataDTO);
        return ResponseEntity.created(new URI("/api/entry-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entry-data : Updates an existing entryData.
     *
     * @param entryDataDTO the entryDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entryDataDTO,
     * or with status 400 (Bad Request) if the entryDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the entryDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entry-data")
    @Timed
    public ResponseEntity<EntryDataDTO> updateEntryData(@RequestBody EntryDataDTO entryDataDTO) throws URISyntaxException {
        log.debug("REST request to update EntryData : {}", entryDataDTO);
        if (entryDataDTO.getId() == null) {
            return createEntryData(entryDataDTO);
        }
        EntryDataDTO result = entryDataService.save(entryDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entryDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entry-data : get all the entryData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entryData in body
     */
    @GetMapping("/entry-data")
    @Timed
    public List<EntryDataDTO> getAllEntryData() {
        log.debug("REST request to get all EntryData");
        return entryDataService.findAll();
        }

    /**
     * GET  /entry-data/:id : get the "id" entryData.
     *
     * @param id the id of the entryDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entryDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entry-data/{id}")
    @Timed
    public ResponseEntity<EntryDataDTO> getEntryData(@PathVariable Long id) {
        log.debug("REST request to get EntryData : {}", id);
        EntryDataDTO entryDataDTO = entryDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entryDataDTO));
    }

    /**
     * DELETE  /entry-data/:id : delete the "id" entryData.
     *
     * @param id the id of the entryDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entry-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntryData(@PathVariable Long id) {
        log.debug("REST request to delete EntryData : {}", id);
        entryDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
