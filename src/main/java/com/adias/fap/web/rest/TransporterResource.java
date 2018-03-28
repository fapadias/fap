package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.TransporterService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.TransporterDTO;
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
 * REST controller for managing Transporter.
 */
@RestController
@RequestMapping("/api")
public class TransporterResource {

    private final Logger log = LoggerFactory.getLogger(TransporterResource.class);

    private static final String ENTITY_NAME = "transporter";

    private final TransporterService transporterService;

    public TransporterResource(TransporterService transporterService) {
        this.transporterService = transporterService;
    }

    /**
     * POST  /transporters : Create a new transporter.
     *
     * @param transporterDTO the transporterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transporterDTO, or with status 400 (Bad Request) if the transporter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transporters")
    @Timed
    public ResponseEntity<TransporterDTO> createTransporter(@RequestBody TransporterDTO transporterDTO) throws URISyntaxException {
        log.debug("REST request to save Transporter : {}", transporterDTO);
        if (transporterDTO.getId() != null) {
            throw new BadRequestAlertException("A new transporter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransporterDTO result = transporterService.save(transporterDTO);
        return ResponseEntity.created(new URI("/api/transporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transporters : Updates an existing transporter.
     *
     * @param transporterDTO the transporterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transporterDTO,
     * or with status 400 (Bad Request) if the transporterDTO is not valid,
     * or with status 500 (Internal Server Error) if the transporterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transporters")
    @Timed
    public ResponseEntity<TransporterDTO> updateTransporter(@RequestBody TransporterDTO transporterDTO) throws URISyntaxException {
        log.debug("REST request to update Transporter : {}", transporterDTO);
        if (transporterDTO.getId() == null) {
            return createTransporter(transporterDTO);
        }
        TransporterDTO result = transporterService.save(transporterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transporterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transporters : get all the transporters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transporters in body
     */
    @GetMapping("/transporters")
    @Timed
    public List<TransporterDTO> getAllTransporters() {
        log.debug("REST request to get all Transporters");
        return transporterService.findAll();
        }

    /**
     * GET  /transporters/:id : get the "id" transporter.
     *
     * @param id the id of the transporterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transporterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transporters/{id}")
    @Timed
    public ResponseEntity<TransporterDTO> getTransporter(@PathVariable Long id) {
        log.debug("REST request to get Transporter : {}", id);
        TransporterDTO transporterDTO = transporterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transporterDTO));
    }

    /**
     * DELETE  /transporters/:id : delete the "id" transporter.
     *
     * @param id the id of the transporterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transporters/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransporter(@PathVariable Long id) {
        log.debug("REST request to delete Transporter : {}", id);
        transporterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
