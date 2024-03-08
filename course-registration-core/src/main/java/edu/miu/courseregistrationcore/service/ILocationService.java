package edu.miu.courseregistrationcore.service;


import edu.miu.courseregistrationcore.domain.Location;

import java.util.List;

public interface ILocationService {
    Location getCourseByLocationName(String locationName);

    Location getLocationById(Long id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void deleteLocationById(Long id);

    Location updateLocation(Long id, Location location);
}
