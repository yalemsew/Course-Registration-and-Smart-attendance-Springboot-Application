package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.Location;
import edu.miu.courseregistrationcore.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourseByLocationName() {
        Location location = new Location();
        location.setName("Main Campus");
        when(locationRepository.findByName("Main Campus")).thenReturn(location);

        Location result = locationService.getCourseByLocationName("Main Campus");
        assertThat(result.getName()).isEqualTo("Main Campus");
    }

    @Test
    public void testGetLocationById() {
        Location location = new Location();
        location.setId(1L);
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        Location result = locationService.getLocationById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetAllLocations() {
        Location location1 = new Location();
        Location location2 = new Location();
        when(locationRepository.findAll()).thenReturn(Arrays.asList(location1, location2));

        List<Location> locations = locationService.getAllLocations();
        assertThat(locations).hasSize(2);
    }

    @Test
    public void testAddLocation() {
        Location location = new Location();
        when(locationRepository.save(location)).thenReturn(location);

        Location result = locationService.addLocation(location);
        assertThat(result).isNotNull();
    }

    @Test
    public void testDeleteLocationById() {
        Long id = 1L;
        doNothing().when(locationRepository).deleteById(id);

        locationService.deleteLocationById(id);
        verify(locationRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateLocation() {
        Long id = 1L;
        Location existingLocation = new Location();
        existingLocation.setId(id);
        existingLocation.setName("Old Name");

        Location newLocation = new Location();
        newLocation.setName("New Name");

        when(locationRepository.findById(id)).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(newLocation);

        Location result = locationService.updateLocation(id, newLocation);
        assertThat(result.getName()).isEqualTo("New Name");
    }
}
