package az.orient.hospitaldemoboot.repository;

import az.orient.hospitaldemoboot.entity.Appointment;
import az.orient.hospitaldemoboot.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut,Long> {


    List<CheckOut> findAllByAppointmentAndActive(Appointment appointment, int value);
}

