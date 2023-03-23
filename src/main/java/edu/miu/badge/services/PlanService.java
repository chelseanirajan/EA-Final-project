package edu.miu.badge.services;

import edu.miu.badge.dto.LocationDTO;
import edu.miu.badge.dto.RequestPlanDTO;
import edu.miu.badge.dto.ResponsePlanDTO;

import java.util.List;

public interface PlanService  {
    ResponsePlanDTO createPlan(RequestPlanDTO requestPlanDTO);
    ResponsePlanDTO getPlanById(Integer id);
    ResponsePlanDTO updatePlan(Integer id, RequestPlanDTO requestPlanDTO);
    String deletePlan(Integer id);
    List<ResponsePlanDTO> getAllPlans();
    List<LocationDTO> getAllLocationsForPlan(Integer id);
}
