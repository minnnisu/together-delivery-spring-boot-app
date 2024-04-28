package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
