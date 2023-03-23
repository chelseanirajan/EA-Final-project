package edu.miu.badge.services.impl;

import edu.miu.badge.domains.TimeSlot;
import edu.miu.badge.dto.TimeSlotDTO;
import edu.miu.badge.exceptions.TimeSlotNotFoundException;
import edu.miu.badge.repositories.TimeSlotRepository;
import edu.miu.badge.services.TimeSlotService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService{
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Override
    public TimeSlot createTimeSlot(TimeSlotDTO timeSlotDTO) {
        TimeSlot ts = new TimeSlot();
        ts.setTimeSlotId(null);
        ts.setStartTime(timeSlotDTO.getStartTime());
        ts.setEndTime(timeSlotDTO.getEndTime());
        ts.setDay(timeSlotDTO.getDay());
        return timeSlotRepository.save(ts);
    }

    @Override
    public TimeSlot getTimeSlotById(Long id) throws TimeSlotNotFoundException{
        return timeSlotRepository.findById(id).orElseThrow(
                () -> new TimeSlotNotFoundException("TimeSlot with the ID " + id + " is not found")
        );
    }

    @Override
    public TimeSlot updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO) throws TimeSlotNotFoundException{
        TimeSlot old = getTimeSlotById(id);
        old.setEndTime(timeSlotDTO.getEndTime());
        old.setStartTime(timeSlotDTO.getStartTime());
        old.setDay(timeSlotDTO.getDay());
        return timeSlotRepository.save(old);
    }

    @Override
    public String deleteTimeSlot(Long id) throws TimeSlotNotFoundException{
        timeSlotRepository.delete(getTimeSlotById(id));
        System.out.println("object deleted");
        return "TimeSlot with the ID : " + id + " has been removed";
    }

    @Override
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }
    
}
