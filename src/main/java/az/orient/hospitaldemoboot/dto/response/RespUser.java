package az.orient.hospitaldemoboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespUser {


    private String username;
    private String fullName;
    @JsonProperty(value="token") //obyet kimi qaytarir
    private RespToken respToken;

}
