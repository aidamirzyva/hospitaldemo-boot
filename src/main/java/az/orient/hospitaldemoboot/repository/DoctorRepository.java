package az.orient.hospitaldemoboot.repository;

import az.orient.hospitaldemoboot.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    List<Doctor> findAllByActive(Integer active);

    Doctor findDoctorByIdAndActive(Long id, Integer active);
}
