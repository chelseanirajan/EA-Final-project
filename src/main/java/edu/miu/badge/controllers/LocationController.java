package edu.miu.badge.controllers;

import edu.miu.badge.domains.Location;
import edu.miu.badge.domains.TimeSlot;
import edu.miu.badge.dto.LocationDTO;
import edu.miu.badge.dto.TimeSlotDTO;
import edu.miu.badge.enumeration.LocationType;
import edu.miu.badge.exceptions.LocationNotFoundException;

import edu.miu.badge.services.LocationService;
import edu.miu.badge.services.TimeSlotService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.badge.repositories.LocationRepository;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping()
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) throws LocationNotFoundException {
        return locationService.getLocationById(id);

    }

    @PostMapping()
    public Location add(@RequestBody LocationDTO locationDTO) {
        return locationService.createLocation(locationDTO);
    }

    @GetMapping("/test")// this is just for testing...
    public String testt() {
        LocationDTO l = new LocationDTO(null, "gym", "this is a gym", 10, LocationType.DORMITORY);
        locationService.createLocation(l);
        return "a generic location has been added";
    }

    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @RequestBody LocationDTO locationDTO) throws LocationNotFoundException {
        return locationService.updateLocation(id, locationDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws LocationNotFoundException {
        return locationService.deleteLocation(id);
    }

    @GetMapping("/{id}/timeslots")
    public List<TimeSlot> getTimeSlots(@PathVariable Long id) throws LocationNotFoundException {
        return locationService.getLocationById(id).getTimeSlots();
    }

    @PostMapping("/{id}/timeslots")
    public List<TimeSlot> getTimeSlots(@PathVariable Long id, @RequestBody List<TimeSlotDTO> timeSlotDTOs) throws LocationNotFoundException {
        Location l = locationService.getLocationById(id);
        for (TimeSlotDTO tsdto : timeSlotDTOs) {
            l.getTimeSlots().add(timeSlotService.createTimeSlot(tsdto));
        }
        return locationRepository.save(l).getTimeSlots();
    }

}
