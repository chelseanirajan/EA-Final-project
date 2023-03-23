package edu.miu.badge.repositories;

import edu.miu.badge.domains.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
}
