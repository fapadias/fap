package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.MetricService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.MetricDTO;
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
 * REST controller for managing Metric.
 */
@RestController
@RequestMapping("/api")
public class MetricResource {

    private final Logger log = LoggerFactory.getLogger(MetricResource.class);

    private static final String ENTITY_NAME = "metric";

    private final MetricService metricService;

    public MetricResource(MetricService metricService) {
        this.metricService = metricService;
    }

    /**
     * POST  /metrics : Create a new metric.
     *
     * @param metricDTO the metricDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metricDTO, or with status 400 (Bad Request) if the metric has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/metrics")
    @Timed
    public ResponseEntity<MetricDTO> createMetric(@RequestBody MetricDTO metricDTO) throws URISyntaxException {
        log.debug("REST request to save Metric : {}", metricDTO);
        if (metricDTO.getId() != null) {
            throw new BadRequestAlertException("A new metric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetricDTO result = metricService.save(metricDTO);
        return ResponseEntity.created(new URI("/api/metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /metrics : Updates an existing metric.
     *
     * @param metricDTO the metricDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metricDTO,
     * or with status 400 (Bad Request) if the metricDTO is not valid,
     * or with status 500 (Internal Server Error) if the metricDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/metrics")
    @Timed
    public ResponseEntity<MetricDTO> updateMetric(@RequestBody MetricDTO metricDTO) throws URISyntaxException {
        log.debug("REST request to update Metric : {}", metricDTO);
        if (metricDTO.getId() == null) {
            return createMetric(metricDTO);
        }
        MetricDTO result = metricService.save(metricDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, metricDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /metrics : get all the metrics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of metrics in body
     */
    @GetMapping("/metrics")
    @Timed
    public List<MetricDTO> getAllMetrics() {
        log.debug("REST request to get all Metrics");
        return metricService.findAll();
        }

    /**
     * GET  /metrics/:id : get the "id" metric.
     *
     * @param id the id of the metricDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metricDTO, or with status 404 (Not Found)
     */
    @GetMapping("/metrics/{id}")
    @Timed
    public ResponseEntity<MetricDTO> getMetric(@PathVariable Long id) {
        log.debug("REST request to get Metric : {}", id);
        MetricDTO metricDTO = metricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(metricDTO));
    }

    /**
     * DELETE  /metrics/:id : delete the "id" metric.
     *
     * @param id the id of the metricDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/metrics/{id}")
    @Timed
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        log.debug("REST request to delete Metric : {}", id);
        metricService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
