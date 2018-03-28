package com.adias.fap.repository;

import com.adias.fap.domain.MetricValue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MetricValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetricValueRepository extends JpaRepository<MetricValue, Long> {

}
