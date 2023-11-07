package az.orient.hospitaldemoboot.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespAppointment {
    private Long id;
    private Integer appointmentNo;
    private Double examinationPrice;
    private RespDoctor respDoctor;
    private RespPatient respPatient;
    private RespLaboratory respLaboratory;
    private Date dataDate;


}
