package edu.miu.badge.repositories;

import edu.miu.badge.domains.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel Tsegay Meresie
 */
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long>{
    
}
