//package edu.miu.courseregistrationcore.repository;
//
//import edu.miu.courseregistrationcore.domain.Location;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//@DataJpaTest
//public class LocationRepositoryTest {
//    @Autowired
//    private LocationRepository locationRepository;
//
//    @Test
//    public void testFindByName() {
//        // Arrange
//        Location location = new Location();
//        location.setName("Test Location");
//        locationRepository.save(location);
//
//        // Act
//        Location foundLocation = locationRepository.findByName("Test Location");
//
//        // Assert
//        Assertions.assertNotNull(foundLocation);
//        Assertions.assertEquals("Test Location", foundLocation.getName());
//    }
//
//    @Test
//    public void testSaveAndFindById() {
//        // Arrange
//        Location location = new Location();
//        location.setName("Another Location");
//        Location savedLocation = locationRepository.save(location);
//
//        // Act
//        Optional<Location> foundLocation = locationRepository.findById(savedLocation.getId());
//
//        // Assert
//        Assertions.assertTrue(foundLocation.isPresent());
//        Assertions.assertEquals("Another Location", foundLocation.get().getName());
//    }
//}
