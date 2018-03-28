package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.DimensionValueService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.DimensionValueDTO;
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
 * REST controller for managing DimensionValue.
 */
@RestController
@RequestMapping("/api")
public class DimensionValueResource {

    private final Logger log = LoggerFactory.getLogger(DimensionValueResource.class);

    private static final String ENTITY_NAME = "dimensionValue";

    private final DimensionValueService dimensionValueService;

    public DimensionValueResource(DimensionValueService dimensionValueService) {
        this.dimensionValueService = dimensionValueService;
    }

    /**
     * POST  /dimension-values : Create a new dimensionValue.
     *
     * @param dimensionValueDTO the dimensionValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dimensionValueDTO, or with status 400 (Bad Request) if the dimensionValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dimension-values")
    @Timed
    public ResponseEntity<DimensionValueDTO> createDimensionValue(@RequestBody DimensionValueDTO dimensionValueDTO) throws URISyntaxException {
        log.debug("REST request to save DimensionValue : {}", dimensionValueDTO);
        if (dimensionValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new dimensionValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DimensionValueDTO result = dimensionValueService.save(dimensionValueDTO);
        return ResponseEntity.created(new URI("/api/dimension-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dimension-values : Updates an existing dimensionValue.
     *
     * @param dimensionValueDTO the dimensionValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dimensionValueDTO,
     * or with status 400 (Bad Request) if the dimensionValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the dimensionValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dimension-values")
    @Timed
    public ResponseEntity<DimensionValueDTO> updateDimensionValue(@RequestBody DimensionValueDTO dimensionValueDTO) throws URISyntaxException {
        log.debug("REST request to update DimensionValue : {}", dimensionValueDTO);
        if (dimensionValueDTO.getId() == null) {
            return createDimensionValue(dimensionValueDTO);
        }
        DimensionValueDTO result = dimensionValueService.save(dimensionValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dimensionValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dimension-values : get all the dimensionValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dimensionValues in body
     */
    @GetMapping("/dimension-values")
    @Timed
    public List<DimensionValueDTO> getAllDimensionValues() {
        log.debug("REST request to get all DimensionValues");
        return dimensionValueService.findAll();
        }

    /**
     * GET  /dimension-values/:id : get the "id" dimensionValue.
     *
     * @param id the id of the dimensionValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dimensionValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dimension-values/{id}")
    @Timed
    public ResponseEntity<DimensionValueDTO> getDimensionValue(@PathVariable Long id) {
        log.debug("REST request to get DimensionValue : {}", id);
        DimensionValueDTO dimensionValueDTO = dimensionValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dimensionValueDTO));
    }

    /**
     * DELETE  /dimension-values/:id : delete the "id" dimensionValue.
     *
     * @param id the id of the dimensionValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dimension-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteDimensionValue(@PathVariable Long id) {
        log.debug("REST request to delete DimensionValue : {}", id);
        dimensionValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
