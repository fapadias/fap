package com.adias.fap.repository;

import com.adias.fap.domain.InvoiceKeyValue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InvoiceKeyValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceKeyValueRepository extends JpaRepository<InvoiceKeyValue, Long> {

}
