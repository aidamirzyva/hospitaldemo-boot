package az.orient.hospitaldemoboot.repository;

import az.orient.hospitaldemoboot.entity.Appointment;
import az.orient.hospitaldemoboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatientAndActive(Patient patient, Integer active);
    Appointment findAppointmentByIdAndActive(Long id, Integer active);



}
