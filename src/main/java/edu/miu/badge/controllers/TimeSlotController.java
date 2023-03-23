package edu.miu.badge.controllers;

import edu.miu.badge.domains.TimeSlot;
import edu.miu.badge.dto.TimeSlotDTO;
import edu.miu.badge.enumeration.DayOfTheWeek;
import edu.miu.badge.exceptions.TimeSlotNotFoundException;
import edu.miu.badge.repositories.TimeSlotRepository;
import edu.miu.badge.services.TimeSlotService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    @Autowired
    private ModelMapper mm;
    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping()
    public List<TimeSlot> getAllTimeslots() {
        return timeSlotService.getAllTimeSlots();
    }

    @GetMapping("/{id}")
    public TimeSlot getById(@PathVariable Long id) throws TimeSlotNotFoundException{
        return timeSlotService.getTimeSlotById(id);
    }

    @PostMapping()
    public TimeSlot add(@RequestBody TimeSlotDTO tsDTO) {
        return timeSlotService.createTimeSlot(tsDTO);
    }

    @GetMapping("/test")// this is just for testing...
    public String testt() {
        timeSlotService.createTimeSlot(new TimeSlotDTO(LocalTime.now(), LocalTime.now().plusHours(2), DayOfTheWeek.FRIDAY));
        return "location added for test";
    }

    @PutMapping("/{id}")
    public TimeSlot update(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) throws TimeSlotNotFoundException {
        return timeSlotService.updateTimeSlot(id, timeSlotDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws TimeSlotNotFoundException{
        return timeSlotService.deleteTimeSlot(id);
    }
}

