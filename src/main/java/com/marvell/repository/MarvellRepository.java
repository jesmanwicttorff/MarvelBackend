package com.marvell.repository;

import com.marvell.domain.Marvell;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Marvell entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarvellRepository extends JpaRepository<Marvell, Long> {}
