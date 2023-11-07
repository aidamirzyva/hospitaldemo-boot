package az.orient.hospitaldemoboot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer appointmentNo;
    private Double examinationPrice;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;


}
