package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.Faculty;
import edu.miu.courseregistrationcore.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacultyService implements IFacultyService {
    @Autowired
    FacultyRepository facultyRepository;

    public Faculty getFacultyByFacultyID(String facultyID) {
        return facultyRepository.findByFacultyID(facultyID);
    }

    public Iterable<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public void addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    public void deleteFacultyByFacultyID(String facultyID) {
        facultyRepository.deleteByFacultyID(facultyID);
    }

    public void updateFacultyByFacultyID(String facultyID, Faculty updatedFaculty) {
        Faculty existingFaculty = facultyRepository.findByFacultyID(facultyID);
        if (updatedFaculty.getFirstName() != null) {
            existingFaculty.setFirstName(updatedFaculty.getFirstName());
        }
        if (updatedFaculty.getLastName() != null) {
            existingFaculty.setLastName(updatedFaculty.getLastName());
        }
        facultyRepository.save(existingFaculty);
    }


}
