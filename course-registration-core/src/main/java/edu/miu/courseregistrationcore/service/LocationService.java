package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.Location;
import edu.miu.courseregistrationcore.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService implements ILocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location getCourseByLocationName(String locationName) {
        return locationRepository.findByName(locationName);
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        return locationRepository.save(location);
    }
}
