package com.adias.fap.repository;

import com.adias.fap.domain.DimensionValue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DimensionValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DimensionValueRepository extends JpaRepository<DimensionValue, Long> {

}
