package edu.miu.badge.repositories;

import edu.miu.badge.domains.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel Tsegay Meresie
 */
public interface LocationRepository extends JpaRepository<Location, Long>{
    
}
