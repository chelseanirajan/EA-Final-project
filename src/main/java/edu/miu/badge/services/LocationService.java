package edu.miu.badge.services;

import edu.miu.badge.domains.Location;
import edu.miu.badge.dto.LocationDTO;
import edu.miu.badge.exceptions.LocationNotFoundException;
import java.util.List;

/**
 *
 * @author Daniel Tsegay Meresie
 */
public interface LocationService {
    Location createLocation(LocationDTO locationDTO);
    Location getLocationById(Long id) throws LocationNotFoundException;
    Location updateLocation(Long id, LocationDTO locationDTO) throws LocationNotFoundException;
    String deleteLocation(Long id)throws LocationNotFoundException;
    List<Location> getAllLocations();
}
