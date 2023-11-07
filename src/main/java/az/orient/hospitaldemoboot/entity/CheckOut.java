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
@Table(name = "checkOut")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalAmount;
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;

}
