package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.DimensionService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.DimensionDTO;
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
 * REST controller for managing Dimension.
 */
@RestController
@RequestMapping("/api")
public class DimensionResource {

    private final Logger log = LoggerFactory.getLogger(DimensionResource.class);

    private static final String ENTITY_NAME = "dimension";

    private final DimensionService dimensionService;

    public DimensionResource(DimensionService dimensionService) {
        this.dimensionService = dimensionService;
    }

    /**
     * POST  /dimensions : Create a new dimension.
     *
     * @param dimensionDTO the dimensionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dimensionDTO, or with status 400 (Bad Request) if the dimension has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dimensions")
    @Timed
    public ResponseEntity<DimensionDTO> createDimension(@RequestBody DimensionDTO dimensionDTO) throws URISyntaxException {
        log.debug("REST request to save Dimension : {}", dimensionDTO);
        if (dimensionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dimension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DimensionDTO result = dimensionService.save(dimensionDTO);
        return ResponseEntity.created(new URI("/api/dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dimensions : Updates an existing dimension.
     *
     * @param dimensionDTO the dimensionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dimensionDTO,
     * or with status 400 (Bad Request) if the dimensionDTO is not valid,
     * or with status 500 (Internal Server Error) if the dimensionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dimensions")
    @Timed
    public ResponseEntity<DimensionDTO> updateDimension(@RequestBody DimensionDTO dimensionDTO) throws URISyntaxException {
        log.debug("REST request to update Dimension : {}", dimensionDTO);
        if (dimensionDTO.getId() == null) {
            return createDimension(dimensionDTO);
        }
        DimensionDTO result = dimensionService.save(dimensionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dimensionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dimensions : get all the dimensions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dimensions in body
     */
    @GetMapping("/dimensions")
    @Timed
    public List<DimensionDTO> getAllDimensions() {
        log.debug("REST request to get all Dimensions");
        return dimensionService.findAll();
        }

    /**
     * GET  /dimensions/:id : get the "id" dimension.
     *
     * @param id the id of the dimensionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dimensionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dimensions/{id}")
    @Timed
    public ResponseEntity<DimensionDTO> getDimension(@PathVariable Long id) {
        log.debug("REST request to get Dimension : {}", id);
        DimensionDTO dimensionDTO = dimensionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dimensionDTO));
    }

    /**
     * DELETE  /dimensions/:id : delete the "id" dimension.
     *
     * @param id the id of the dimensionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dimensions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDimension(@PathVariable Long id) {
        log.debug("REST request to delete Dimension : {}", id);
        dimensionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
