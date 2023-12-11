package com.marvell.repository;

import com.marvell.domain.Distribuidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Distribuidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistribuidorRepository extends JpaRepository<Distribuidor, Long> {}
