package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.MetricValueService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.MetricValueDTO;
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
 * REST controller for managing MetricValue.
 */
@RestController
@RequestMapping("/api")
public class MetricValueResource {

    private final Logger log = LoggerFactory.getLogger(MetricValueResource.class);

    private static final String ENTITY_NAME = "metricValue";

    private final MetricValueService metricValueService;

    public MetricValueResource(MetricValueService metricValueService) {
        this.metricValueService = metricValueService;
    }

    /**
     * POST  /metric-values : Create a new metricValue.
     *
     * @param metricValueDTO the metricValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metricValueDTO, or with status 400 (Bad Request) if the metricValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/metric-values")
    @Timed
    public ResponseEntity<MetricValueDTO> createMetricValue(@RequestBody MetricValueDTO metricValueDTO) throws URISyntaxException {
        log.debug("REST request to save MetricValue : {}", metricValueDTO);
        if (metricValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new metricValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetricValueDTO result = metricValueService.save(metricValueDTO);
        return ResponseEntity.created(new URI("/api/metric-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /metric-values : Updates an existing metricValue.
     *
     * @param metricValueDTO the metricValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metricValueDTO,
     * or with status 400 (Bad Request) if the metricValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the metricValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/metric-values")
    @Timed
    public ResponseEntity<MetricValueDTO> updateMetricValue(@RequestBody MetricValueDTO metricValueDTO) throws URISyntaxException {
        log.debug("REST request to update MetricValue : {}", metricValueDTO);
        if (metricValueDTO.getId() == null) {
            return createMetricValue(metricValueDTO);
        }
        MetricValueDTO result = metricValueService.save(metricValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, metricValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /metric-values : get all the metricValues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of metricValues in body
     */
    @GetMapping("/metric-values")
    @Timed
    public List<MetricValueDTO> getAllMetricValues() {
        log.debug("REST request to get all MetricValues");
        return metricValueService.findAll();
        }

    /**
     * GET  /metric-values/:id : get the "id" metricValue.
     *
     * @param id the id of the metricValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metricValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/metric-values/{id}")
    @Timed
    public ResponseEntity<MetricValueDTO> getMetricValue(@PathVariable Long id) {
        log.debug("REST request to get MetricValue : {}", id);
        MetricValueDTO metricValueDTO = metricValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(metricValueDTO));
    }

    /**
     * DELETE  /metric-values/:id : delete the "id" metricValue.
     *
     * @param id the id of the metricValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/metric-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteMetricValue(@PathVariable Long id) {
        log.debug("REST request to delete MetricValue : {}", id);
        metricValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
