package az.orient.hospitaldemoboot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespLaboratory {
    private Long id;
    private String analysisName;
    private String analysisResult;
    private Double labAmount;
}
