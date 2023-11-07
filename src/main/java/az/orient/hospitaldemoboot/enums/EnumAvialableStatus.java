package az.orient.hospitaldemoboot.enums;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public enum EnumAvialableStatus {

    ACTIVE(1), DEACTIVE(0);


    public int value;
}
