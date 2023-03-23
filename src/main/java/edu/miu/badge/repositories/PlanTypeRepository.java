package edu.miu.badge.repositories;

import edu.miu.badge.domains.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanTypeRepository extends JpaRepository<PlanType, Integer> {
}
