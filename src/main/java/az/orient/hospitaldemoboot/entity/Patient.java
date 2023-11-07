package az.orient.hospitaldemoboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "patient")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String pin;
    private String seria;
    private Date dob;
    private String phone;
    private String address;
    private String blood;
    private String email;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;


}