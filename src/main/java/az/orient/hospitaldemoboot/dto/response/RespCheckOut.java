package az.orient.hospitaldemoboot.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespCheckOut {
    private Long id;
    private RespAppointment appointment;
    private Double totalAmount;
    private Date operationDate;
}
