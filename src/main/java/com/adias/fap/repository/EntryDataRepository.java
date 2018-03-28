package com.adias.fap.repository;

import com.adias.fap.domain.EntryData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EntryData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryDataRepository extends JpaRepository<EntryData, Long> {

}
