package edu.miu.badge.services.impl;

import edu.miu.badge.domains.*;
import edu.miu.badge.dto.LocationDTO;
import edu.miu.badge.dto.RequestPlanDTO;
import edu.miu.badge.dto.ResponsePlanDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.repositories.LocationRepository;
import edu.miu.badge.repositories.PlanRepository;
import edu.miu.badge.repositories.PlanTypeRepository;
import edu.miu.badge.repositories.RoleRepository;
import edu.miu.badge.services.PlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PlanTypeRepository planTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponsePlanDTO getPlanById(Integer id) {
        Optional<Plan> plan = planRepository.findById(id);
        if(plan.isPresent()){
            return modelMapper.map(plan.get(), ResponsePlanDTO.class);
        }
        throw new ResourceNotFoundException("Plan with id " + id + " not found");
    }

    @Override
    public ResponsePlanDTO updatePlan(Integer id, RequestPlanDTO requestPlanDTO) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if(planOptional.isPresent()){
            Plan toBeUpdated = planOptional.get();
            toBeUpdated.setName(requestPlanDTO.getName());
            toBeUpdated.setDescription(requestPlanDTO.getDescription());
            toBeUpdated.setLocations(getLocationsForPlan(requestPlanDTO.getLocationsId()));
            toBeUpdated.setAllowedRoles(getRolesForPlan(requestPlanDTO.getAllowedRolesId()));
            toBeUpdated.setPlanTypes(getPlanTypesForPlan(requestPlanDTO.getPlanTypesId()));
            return modelMapper.map(planRepository.save(toBeUpdated), ResponsePlanDTO.class);
        }
        else{
            throw new ResourceNotFoundException("Plan with an id " + id + " doesn't exist");
        }
    }

    @Override
    public String deletePlan(Integer id) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if(planOptional.isPresent()){
           planRepository.delete(planOptional.get());
           return "Plan with id " + id + " deleted!";
        }
        else{
            throw new ResourceNotFoundException("Plan with an id " + id + " doesn't exist");
        }
    }

    @Override
    public List<ResponsePlanDTO> getAllPlans() {
        return planRepository.findAll().stream()
                .map(plan -> modelMapper.map(plan, ResponsePlanDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getAllLocationsForPlan(Integer id) {
        List<LocationDTO> locationDTOS = new ArrayList<>();
        Optional<Plan> planOptional = planRepository.findById(id);
        if(planOptional.isPresent()){
            for (Location l: planOptional.get().getLocations()) {
                locationDTOS.add(modelMapper.map(l, LocationDTO.class));
            }
            return locationDTOS;
        }else {
            throw new ResourceNotFoundException("Plan with an id " + id + " doesn't exist");
        }
    }

    @Override
    public ResponsePlanDTO createPlan(RequestPlanDTO requestPlanDTO) {
        Plan plan = new Plan();
        plan.setName(requestPlanDTO.getName());
        plan.setDescription(requestPlanDTO.getDescription());
        plan.setPlanTypes(getPlanTypesForPlan(requestPlanDTO.getPlanTypesId()));
        plan.setAllowedRoles(getRolesForPlan(requestPlanDTO.getAllowedRolesId()));
        plan.setLocations(getLocationsForPlan(requestPlanDTO.getLocationsId()));
        return modelMapper.map(planRepository.save(plan), ResponsePlanDTO.class);
    }

    private List<Location> getLocationsForPlan(List<Long> locationIds){
        List<Location> locations = new ArrayList<>();
        for(Long locId: locationIds){
            Optional<Location> locationOptional = locationRepository.findById(locId);
            if (locationOptional.isPresent()){
                locations.add(locationOptional.get());
            }else {
                throw new ResourceNotFoundException("Location with location id " + " not found");
            }
        }
        return locations;
    }

    private List<Role> getRolesForPlan(List<Integer> roleIds){
        List<Role> roles = new ArrayList<>();
        for (Integer roleId: roleIds){
            Optional<Role> roleOptional = roleRepository.findById(roleId);
            if (roleOptional.isPresent()){
                roles.add(roleOptional.get());
            }else {
                throw new ResourceNotFoundException("Allowed Role with id " + roleId + " not found");
            }
        }
        return roles;
    }
    private List<PlanType> getPlanTypesForPlan(List<Integer> planTypeId){
        List<PlanType> planTypes = new ArrayList<>();
        for (Integer ptId: planTypeId) {
            Optional<PlanType>  planTypeOptional= planTypeRepository.findById(ptId);
            if(planTypeOptional.isPresent()){
                planTypes.add(planTypeOptional.get());
            }else {
                throw new ResourceNotFoundException("Plan type with id " + ptId + " not found!");
            }
        }
        return planTypes;
    }

}
