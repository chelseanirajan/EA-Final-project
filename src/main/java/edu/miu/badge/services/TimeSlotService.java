package edu.miu.badge.services;

import edu.miu.badge.domains.TimeSlot;
import edu.miu.badge.dto.TimeSlotDTO;
import edu.miu.badge.exceptions.TimeSlotNotFoundException;
import java.util.List;

/**
 *
 * @author Daniel Tsegay Meresie
 */
public interface TimeSlotService {
    TimeSlot createTimeSlot(TimeSlotDTO timeSlotDTO);
    TimeSlot getTimeSlotById(Long id) throws TimeSlotNotFoundException;
    TimeSlot updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO) throws TimeSlotNotFoundException;
    String deleteTimeSlot(Long id) throws TimeSlotNotFoundException;
    List<TimeSlot> getAllTimeSlots();
}
