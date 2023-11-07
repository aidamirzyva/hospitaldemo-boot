package az.orient.hospitaldemoboot.dto.request;

import lombok.Data;

@Data
public class ReqToken {

    private Long userId;
    private String token;
}
