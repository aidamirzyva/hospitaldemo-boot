package az.orient.hospitaldemoboot.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqAppointment {

    private Integer appointmentNo;
    private Double examinationPrice;
    private Long patientId;
    private Long doctorId;
    private Long laboratoryId;

}
