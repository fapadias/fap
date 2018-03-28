package com.adias.fap.repository;

import com.adias.fap.domain.CostGride;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CostGride entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostGrideRepository extends JpaRepository<CostGride, Long> {

}
