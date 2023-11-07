package az.orient.hospitaldemoboot.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReqPatient {

    private Long patientId;
    private String name;
    private String surname;
    private String pin;
    private String seria;
    private Date dob;
    private String phone;
    private String address;
    private String blood;
    private String email;
    @JsonProperty(value="token")
    private ReqToken reqToken;
}
