package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.CostGrideService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.CostGrideDTO;
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
 * REST controller for managing CostGride.
 */
@RestController
@RequestMapping("/api")
public class CostGrideResource {

    private final Logger log = LoggerFactory.getLogger(CostGrideResource.class);

    private static final String ENTITY_NAME = "costGride";

    private final CostGrideService costGrideService;

    public CostGrideResource(CostGrideService costGrideService) {
        this.costGrideService = costGrideService;
    }

    /**
     * POST  /cost-grides : Create a new costGride.
     *
     * @param costGrideDTO the costGrideDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new costGrideDTO, or with status 400 (Bad Request) if the costGride has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cost-grides")
    @Timed
    public ResponseEntity<CostGrideDTO> createCostGride(@RequestBody CostGrideDTO costGrideDTO) throws URISyntaxException {
        log.debug("REST request to save CostGride : {}", costGrideDTO);
        if (costGrideDTO.getId() != null) {
            throw new BadRequestAlertException("A new costGride cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostGrideDTO result = costGrideService.save(costGrideDTO);
        return ResponseEntity.created(new URI("/api/cost-grides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cost-grides : Updates an existing costGride.
     *
     * @param costGrideDTO the costGrideDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated costGrideDTO,
     * or with status 400 (Bad Request) if the costGrideDTO is not valid,
     * or with status 500 (Internal Server Error) if the costGrideDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cost-grides")
    @Timed
    public ResponseEntity<CostGrideDTO> updateCostGride(@RequestBody CostGrideDTO costGrideDTO) throws URISyntaxException {
        log.debug("REST request to update CostGride : {}", costGrideDTO);
        if (costGrideDTO.getId() == null) {
            return createCostGride(costGrideDTO);
        }
        CostGrideDTO result = costGrideService.save(costGrideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, costGrideDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cost-grides : get all the costGrides.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of costGrides in body
     */
    @GetMapping("/cost-grides")
    @Timed
    public List<CostGrideDTO> getAllCostGrides() {
        log.debug("REST request to get all CostGrides");
        return costGrideService.findAll();
        }

    /**
     * GET  /cost-grides/:id : get the "id" costGride.
     *
     * @param id the id of the costGrideDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the costGrideDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cost-grides/{id}")
    @Timed
    public ResponseEntity<CostGrideDTO> getCostGride(@PathVariable Long id) {
        log.debug("REST request to get CostGride : {}", id);
        CostGrideDTO costGrideDTO = costGrideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(costGrideDTO));
    }

    /**
     * DELETE  /cost-grides/:id : delete the "id" costGride.
     *
     * @param id the id of the costGrideDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cost-grides/{id}")
    @Timed
    public ResponseEntity<Void> deleteCostGride(@PathVariable Long id) {
        log.debug("REST request to delete CostGride : {}", id);
        costGrideService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
