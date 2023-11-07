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
@Table(name = "laboratory")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String analysisName;
    private String analysisResult;
    private Double labAmount;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
