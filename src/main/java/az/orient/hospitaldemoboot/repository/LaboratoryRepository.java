package az.orient.hospitaldemoboot.repository;

import az.orient.hospitaldemoboot.entity.Laboratory;
import az.orient.hospitaldemoboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    List<Laboratory> findAllByActive(Integer active);

    List<Laboratory> findAllByPatientAndActive(Patient patient, Integer active);

    Laboratory findLaboratoryByIdAndActive(Long laboratoryId, int value);
}
