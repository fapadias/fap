package com.adias.fap.repository;

import com.adias.fap.domain.Transporter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Transporter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {

    @Query("select transporter from Transporter transporter where transporter.user.login = ?#{principal.username}")
    List<Transporter> findByUserIsCurrentUser();

}
