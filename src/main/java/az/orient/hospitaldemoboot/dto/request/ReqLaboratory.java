package az.orient.hospitaldemoboot.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqLaboratory {
    private Long laboratoryId;
    private String analysisName;
    private Long patientId;
    private String analysisResult;
    private Double labAmount;

}
